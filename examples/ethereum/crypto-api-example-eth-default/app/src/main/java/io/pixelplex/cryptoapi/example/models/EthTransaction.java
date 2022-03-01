package io.pixelplex.cryptoapi.example.models;

import org.spongycastle.jcajce.provider.digest.Keccak;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.Arrays;

import static java.lang.reflect.Array.getLength;

public class EthTransaction {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final byte[] ZERO_BYTE_ARRAY = new byte[]{0};
    public static final int HASH_LENGTH = 32;
    public static final int ADDRESS_LENGTH = 20;
    private static final BigInteger DEFAULT_GAS_PRICE = new BigInteger("10000000000000");
    private static final BigInteger DEFAULT_BALANCE_GAS = new BigInteger("21000");
    /**
     * Since EIP-155, we could encode chainId in V
     */
    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;
    protected byte[] sendAddress;
    /* Tx in encoded form */
    protected byte[] rlpEncoded;
    /* Indicates if this transaction has been parsed
     * from the RLP-encoded data */
    protected boolean parsed = false;
    /* SHA3 hash of the RLP encoded transaction */
    private byte[] hash;
    /* a counter used to make sure each transaction can only be processed once */
    private byte[] nonce;
    /* the amount of ether to transfer (calculated as wei) */
    private byte[] value;
    /* the address of the destination account
     * In creation transaction the receive address is - 0 */
    private byte[] receiveAddress;
    /* the amount of ether to pay as a transaction fee
     * to the miner for each unit of gas */
    private byte[] gasPrice;
    /* the amount of "gas" to allow for the computation.
     * Gas is the fuel of the computational engine;
     * every computational step taken and every byte added
     * to the state or transaction list consumes some gas. */
    private byte[] gasLimit;
    /* An unlimited size byte array specifying
     * input [data] of the message call or
     * Initialization code for a new contract */
    private byte[] data;
    private Integer chainId = null;
    /* the elliptic curve signature
     * (including public key recovery bits) */
    private EthECKey.ECDSASignature signature;
    private byte[] rlpRaw;

    public EthTransaction(byte[] rawData) {
        this.rlpEncoded = rawData;
        parsed = false;
    }

    public EthTransaction(byte[] nonce, byte[] gasPrice, byte[] gasLimit, byte[] receiveAddress, byte[] value, byte[] data,
                          Integer chainId) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.receiveAddress = receiveAddress;
        if (isSingleZero(value)) {
            this.value = EMPTY_BYTE_ARRAY;
        } else {
            this.value = value;
        }
        this.data = data;
        this.chainId = chainId;

        if (receiveAddress == null) {
            this.receiveAddress = EMPTY_BYTE_ARRAY;
        }

        parsed = true;
    }

    /**
     * Warning: this transaction would not be protected by replay-attack protection mechanism
     * Use {@link EthTransaction#EthTransaction(byte[], byte[], byte[], byte[], byte[], byte[], Integer)} constructor instead
     * and specify the desired chainID
     */
    public EthTransaction(byte[] nonce, byte[] gasPrice, byte[] gasLimit, byte[] receiveAddress, byte[] value, byte[] data) {
        this(nonce, gasPrice, gasLimit, receiveAddress, value, data, null);
    }

    public EthTransaction(byte[] nonce, byte[] gasPrice, byte[] gasLimit, byte[] receiveAddress, byte[] value, byte[] data,
                          byte[] r, byte[] s, byte v, Integer chainId) {
        this(nonce, gasPrice, gasLimit, receiveAddress, value, data, chainId);
        this.signature = EthECKey.ECDSASignature.fromComponents(r, s, v);
    }

    /**
     * Warning: this transaction would not be protected by replay-attack protection mechanism
     * Use {@link EthTransaction#EthTransaction(byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte, Integer)}
     * constructor instead and specify the desired chainID
     */
    public EthTransaction(byte[] nonce, byte[] gasPrice, byte[] gasLimit, byte[] receiveAddress, byte[] value, byte[] data,
                          byte[] r, byte[] s, byte v) {
        this(nonce, gasPrice, gasLimit, receiveAddress, value, data, r, s, v, null);
    }

    public static boolean isEmpty(final byte[] array) {
        return array != null && getLength(array) == 0;
    }

    /**
     * @deprecated Use {@link EthTransaction#createDefault(String, BigInteger, BigInteger, Integer)} instead
     */
    public static EthTransaction createDefault(String to, BigInteger amount, BigInteger nonce) {
        return create(to, amount, nonce, DEFAULT_GAS_PRICE, DEFAULT_BALANCE_GAS);
    }

    public static EthTransaction createDefault(String to, BigInteger amount, BigInteger nonce, Integer chainId) {
        return create(to, amount, nonce, DEFAULT_GAS_PRICE, DEFAULT_BALANCE_GAS, chainId);
    }

    /**
     * @deprecated use {@link EthTransaction#create(String, BigInteger, BigInteger, BigInteger, BigInteger, Integer)} instead
     */
    public static EthTransaction create(String to, BigInteger amount, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthTransaction(BigIntegers.asUnsignedByteArray(nonce),
                BigIntegers.asUnsignedByteArray(gasPrice),
                BigIntegers.asUnsignedByteArray(gasLimit),
                Hex.decode(to),
                BigIntegers.asUnsignedByteArray(amount),
                null);
    }

    public static EthTransaction create(String to, BigInteger amount, BigInteger nonce, BigInteger gasPrice,
                                        BigInteger gasLimit, Integer chainId) {
        return new EthTransaction(BigIntegers.asUnsignedByteArray(nonce),
                BigIntegers.asUnsignedByteArray(gasPrice),
                BigIntegers.asUnsignedByteArray(gasLimit),
                Hex.decode(to),
                BigIntegers.asUnsignedByteArray(amount),
                null,
                chainId);
    }

    public boolean isSingleZero(byte[] array) {
        return (array.length == 1 && array[0] == 0);
    }

    private Integer extractChainIdFromV(BigInteger bv) {
        if (bv.bitLength() > 31)
            return Integer.MAX_VALUE; // chainId is limited to 31 bits, longer are not valid for now
        long v = bv.longValue();
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) return null;
        return (int) ((v - CHAIN_ID_INC) / 2);
    }

    private byte getRealV(BigInteger bv) {
        if (bv.bitLength() > 31)
            return 0; // chainId is limited to 31 bits, longer are not valid for now
        long v = bv.longValue();
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) return (byte) v;
        byte realV = LOWER_REAL_V;
        int inc = 0;
        if ((int) v % 2 == 0) inc = 1;
        return (byte) (realV + inc);
    }

    public synchronized void verify() {
        rlpParse();
        validate();
    }

    public synchronized void rlpParse() {
        if (parsed) return;
        try {
            RLPList decodedTxList = RLP.decode2(rlpEncoded);
            RLPList transaction = (RLPList) decodedTxList.get(0);

            // Basic verification
            if (transaction.size() > 9) throw new RuntimeException("Too many RLP elements");
            for (RLPElement rlpElement : transaction) {
                if (!(rlpElement instanceof RLPItem))
                    throw new RuntimeException("Transaction RLP elements shouldn't be lists");
            }

            this.nonce = transaction.get(0).getRlpData();
            this.gasPrice = transaction.get(1).getRlpData();
            this.gasLimit = transaction.get(2).getRlpData();
            this.receiveAddress = transaction.get(3).getRlpData();
            this.value = transaction.get(4).getRlpData();
            this.data = transaction.get(5).getRlpData();
            // only parse signature in case tx is signed
            if (transaction.get(6).getRlpData() != null) {
                byte[] vData = transaction.get(6).getRlpData();
                BigInteger v = bytesToBigInteger(vData);
                this.chainId = extractChainIdFromV(v);
                byte[] r = transaction.get(7).getRlpData();
                byte[] s = transaction.get(8).getRlpData();
                this.signature = EthECKey.ECDSASignature.fromComponents(r, s, getRealV(v));
            } else {
//                logger.debug("RLP encoded tx is not signed!");
            }
            this.parsed = true;
            this.hash = getHash();
        } catch (Exception e) {
            throw new RuntimeException("Error on parsing RLP", e);
        }
    }

    public BigInteger bytesToBigInteger(byte[] bb) {
        return bb.length == 0 ? BigInteger.ZERO : new BigInteger(1, bb);
    }

    private void validate() {
        if (getNonce().length > HASH_LENGTH) throw new RuntimeException("Nonce is not valid");
        if (receiveAddress != null && receiveAddress.length != 0 && receiveAddress.length != ADDRESS_LENGTH)
            throw new RuntimeException("Receive address is not valid");
        if (gasLimit.length > HASH_LENGTH)
            throw new RuntimeException("Gas Limit is not valid");
        if (gasPrice != null && gasPrice.length > HASH_LENGTH)
            throw new RuntimeException("Gas Price is not valid");
        if (value != null && value.length > HASH_LENGTH)
            throw new RuntimeException("Value is not valid");
        if (getSignature() != null) {
            if (BigIntegers.asUnsignedByteArray(signature.r).length > HASH_LENGTH)
                throw new RuntimeException("Signature R is not valid");
            if (BigIntegers.asUnsignedByteArray(signature.s).length > HASH_LENGTH)
                throw new RuntimeException("Signature S is not valid");
            if (getSender() != null && getSender().length != ADDRESS_LENGTH)
                throw new RuntimeException("Sender is not valid");
        }
    }

    public boolean isParsed() {
        return parsed;
    }

    public byte[] getHash() {
        if (!isEmpty(hash)) return hash;

        rlpParse();
        byte[] plainMsg = this.getEncoded();

        Keccak.DigestKeccak keccak = new Keccak.Digest256();
        keccak.update(plainMsg, 0, plainMsg.length);
        return keccak.digest();
    }

    public byte[] getRawHash() {
        rlpParse();
        byte[] plainMsg = this.getEncodedRaw();

        Keccak.DigestKeccak keccak = new Keccak.Digest256();
        keccak.update(plainMsg, 0, plainMsg.length);
        return keccak.digest();
    }

    public byte[] getNonce() {
        rlpParse();

        return nonce == null ? ZERO_BYTE_ARRAY : nonce;
    }

    protected void setNonce(byte[] nonce) {
        this.nonce = nonce;
        parsed = true;
    }

    public boolean isValueTx() {
        rlpParse();
        return value != null;
    }

    public byte[] getValue() {
        rlpParse();
        return value == null ? ZERO_BYTE_ARRAY : value;
    }

    protected void setValue(byte[] value) {
        this.value = value;
        parsed = true;
    }

    public byte[] getReceiveAddress() {
        rlpParse();
        return receiveAddress;
    }

    protected void setReceiveAddress(byte[] receiveAddress) {
        this.receiveAddress = receiveAddress;
        parsed = true;
    }

    public byte[] getGasPrice() {
        rlpParse();
        return gasPrice == null ? ZERO_BYTE_ARRAY : gasPrice;
    }

    protected void setGasPrice(byte[] gasPrice) {
        this.gasPrice = gasPrice;
        parsed = true;
    }

    public byte[] getGasLimit() {
        rlpParse();
        return gasLimit == null ? ZERO_BYTE_ARRAY : gasLimit;
    }

    protected void setGasLimit(byte[] gasLimit) {
        this.gasLimit = gasLimit;
        parsed = true;
    }

    public long nonZeroDataBytes() {
        if (data == null) return 0;
        int counter = 0;
        for (final byte aData : data) {
            if (aData != 0) ++counter;
        }
        return counter;
    }

    public long zeroDataBytes() {
        if (data == null) return 0;
        int counter = 0;
        for (final byte aData : data) {
            if (aData == 0) ++counter;
        }
        return counter;
    }

    /*
     * Crypto
     */

    public byte[] getData() {
        rlpParse();
        return data;
    }

    protected void setData(byte[] data) {
        this.data = data;
        parsed = true;
    }

    public EthECKey.ECDSASignature getSignature() {
        rlpParse();
        return signature;
    }

    public boolean isContractCreation() {
        rlpParse();
        return this.receiveAddress == null || Arrays.equals(this.receiveAddress, EMPTY_BYTE_ARRAY);
    }

    public EthECKey getKey() {
        byte[] hash = getRawHash();
        return EthECKey.recoverFromSignature(signature.v, signature, hash);
    }

    public synchronized byte[] getSender() {
        try {
            if (sendAddress == null) {
                sendAddress = EthECKey.signatureToAddress(getRawHash(), getSignature());
            }
            return sendAddress;
        } catch (SignatureException e) {
//            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Integer getChainId() {
        rlpParse();
        return chainId == null ? null : (int) chainId;
    }

    /**
     * @deprecated should prefer #sign(ECKey) over this method
     */
    public void sign(byte[] privKeyBytes) throws EthECKey.MissingPrivateKeyException {
        sign(EthECKey.fromPrivate(privKeyBytes));
    }

    public void sign(EthECKey key) throws EthECKey.MissingPrivateKeyException {
        this.signature = key.sign(this.getRawHash());
        this.rlpEncoded = null;
    }

    @Override
    public String toString() {
        return toString(Integer.MAX_VALUE);
    }

    public String toString(int maxDataSize) {
        rlpParse();
        String dataS;
        if (data == null) {
            dataS = "";
        } else if (data.length < maxDataSize) {
            dataS = ByteUtils.toHexString(data);
        } else {
            dataS = ByteUtils.toHexString(Arrays.copyOfRange(data, 0, maxDataSize)) +
                    "... (" + data.length + " bytes)";
        }
        return "TransactionData [" + "hash=" + ByteUtils.toHexString(hash) +
                "  nonce=" + ByteUtils.toHexString(nonce) +
                ", gasPrice=" + ByteUtils.toHexString(gasPrice) +
                ", gas=" + ByteUtils.toHexString(gasLimit) +
                ", receiveAddress=" + ByteUtils.toHexString(receiveAddress) +
                ", sendAddress=" + ByteUtils.toHexString(getSender()) +
                ", value=" + ByteUtils.toHexString(value) +
                ", data=" + dataS +
                ", signatureV=" + (signature == null ? "" : signature.v) +
                ", signatureR=" + (signature == null ? "" : ByteUtils.toHexString(BigIntegers.asUnsignedByteArray(signature.r))) +
                ", signatureS=" + (signature == null ? "" : ByteUtils.toHexString(BigIntegers.asUnsignedByteArray(signature.s))) +
                "]";
    }

    /**
     * For signatures you have to keep also
     * RLP of the transaction without any signature data
     */
    public byte[] getEncodedRaw() {

        rlpParse();
        if (rlpRaw != null) return rlpRaw;

        // parse null as 0 for nonce
        byte[] nonce = null;
        if (this.nonce == null || this.nonce.length == 1 && this.nonce[0] == 0) {
            nonce = RLP.encodeElement(null);
        } else {
            nonce = RLP.encodeElement(this.nonce);
        }
        byte[] gasPrice = RLP.encodeElement(this.gasPrice);
        byte[] gasLimit = RLP.encodeElement(this.gasLimit);
        byte[] receiveAddress = RLP.encodeElement(this.receiveAddress);
        byte[] value = RLP.encodeElement(this.value);
        byte[] data = RLP.encodeElement(this.data);

        // Since EIP-155 use chainId for v
        if (chainId == null) {
            rlpRaw = RLP.encodeList(nonce, gasPrice, gasLimit, receiveAddress,
                    value, data);
        } else {
            byte[] v, r, s;
            v = RLP.encodeInt(chainId);
            r = RLP.encodeElement(EMPTY_BYTE_ARRAY);
            s = RLP.encodeElement(EMPTY_BYTE_ARRAY);
            rlpRaw = RLP.encodeList(nonce, gasPrice, gasLimit, receiveAddress,
                    value, data, v, r, s);
        }
        return rlpRaw;
    }

    public byte[] getEncoded() {

        if (rlpEncoded != null) return rlpEncoded;

        // parse null as 0 for nonce
        byte[] nonce = null;
        if (this.nonce == null || this.nonce.length == 1 && this.nonce[0] == 0) {
            nonce = RLP.encodeElement(null);
        } else {
            nonce = RLP.encodeElement(this.nonce);
        }
        byte[] gasPrice = RLP.encodeElement(this.gasPrice);
        byte[] gasLimit = RLP.encodeElement(this.gasLimit);
        byte[] receiveAddress = RLP.encodeElement(this.receiveAddress);
        byte[] value = RLP.encodeElement(this.value);
        byte[] data = RLP.encodeElement(this.data);

        byte[] v, r, s;

        if (signature != null) {
            int encodeV;
            if (chainId == null) {
                encodeV = signature.v;
            } else {
                encodeV = signature.v - LOWER_REAL_V;
                encodeV += chainId * 2 + CHAIN_ID_INC;
            }
            v = RLP.encodeInt(encodeV);
            r = RLP.encodeElement(BigIntegers.asUnsignedByteArray(signature.r));
            s = RLP.encodeElement(BigIntegers.asUnsignedByteArray(signature.s));
        } else {
            // Since EIP-155 use chainId for v
            v = chainId == null ? RLP.encodeElement(EMPTY_BYTE_ARRAY) : RLP.encodeInt(chainId);
            r = RLP.encodeElement(EMPTY_BYTE_ARRAY);
            s = RLP.encodeElement(EMPTY_BYTE_ARRAY);
        }

        this.rlpEncoded = RLP.encodeList(nonce, gasPrice, gasLimit,
                receiveAddress, value, data, v, r, s);

        this.hash = this.getHash();

        return rlpEncoded;
    }

    @Override
    public int hashCode() {

        byte[] hash = this.getHash();
        int hashCode = 0;

        for (int i = 0; i < hash.length; ++i) {
            hashCode += hash[i] * i;
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof EthTransaction)) return false;
        EthTransaction tx = (EthTransaction) obj;

        return tx.hashCode() == this.hashCode();
    }
}
