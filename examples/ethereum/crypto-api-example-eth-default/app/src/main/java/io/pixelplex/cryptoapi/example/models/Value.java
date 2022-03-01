package io.pixelplex.cryptoapi.example.models;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Class to encapsulate an object and provide utilities for conversion
 */
public class Value {

    private Object value;
    private byte[] rlp;

    private boolean decoded = false;

    public Value() {
    }

    public Value(Object obj) {

        this.decoded = true;
        if (obj == null) return;

        if (obj instanceof Value) {
            this.value = ((Value) obj).asObj();
        } else {
            this.value = obj;
        }
    }

    public void init(byte[] rlp) {
        this.rlp = rlp;
    }

    /* *****************
     *      Convert
     * *****************/

    public Object asObj() {
        decode();
        return value;
    }

    public List<Object> asList() {
        decode();
        Object[] valueArray = (Object[]) value;
        return Arrays.asList(valueArray);
    }

    public int asInt() {
        decode();
        if (isInt()) {
            return (Integer) value;
        } else if (isBytes()) {
            return new BigInteger(1, asBytes()).intValue();
        }
        return 0;
    }

    public long asLong() {
        decode();
        if (isLong()) {
            return (Long) value;
        } else if (isBytes()) {
            return new BigInteger(1, asBytes()).longValue();
        }
        return 0;
    }

    public BigInteger asBigInt() {
        decode();
        return (BigInteger) value;
    }

    public String asString() {
        decode();
        if (isBytes()) {
            return new String((byte[]) value);
        } else if (isString()) {
            return (String) value;
        }
        return "";
    }

    public byte[] asBytes() {
        decode();
        if (isBytes()) {
            return (byte[]) value;
        } else if (isString()) {
            return asString().getBytes();
        }
        return new byte[0];
    }

    public String getHex() {
        return Hex.toHexString(this.encode());
    }

    public byte[] getData() {
        return this.encode();
    }


    public int[] asSlice() {
        return (int[]) value;
    }

    public Value get(int index) {
        if (isList()) {
            // Guard for OutOfBounds
            if (asList().size() <= index) {
                return new Value(null);
            }
            if (index < 0) {
                throw new RuntimeException("Negative index not allowed");
            }
            return new Value(asList().get(index));
        }
        // If this wasn't a slice you probably shouldn't be using this function
        return new Value(null);
    }

    /* *****************
     *      Utility
     * *****************/

    public void decode() {
        if (!this.decoded) {
            this.value = RLP.decode(rlp, 0).getDecoded();
            this.decoded = true;
        }
    }

    public byte[] encode() {
        if (rlp == null)
            rlp = RLP.encode(value);
        return rlp;
    }

    /* *****************
     *      Checks
     * *****************/

    public boolean isList() {
        decode();
        return value != null && value.getClass().isArray() && !value.getClass().getComponentType().isPrimitive();
    }

    public boolean isString() {
        decode();
        return value instanceof String;
    }

    public boolean isInt() {
        decode();
        return value instanceof Integer;
    }

    public boolean isLong() {
        decode();
        return value instanceof Long;
    }

    public boolean isBytes() {
        decode();
        return value instanceof byte[];
    }

    public boolean isHashCode() {
        decode();
        return this.asBytes().length == 32;
    }

    public boolean isNull() {
        decode();
        return value == null;
    }

    public boolean isEmpty() {
        decode();
        if (isNull()) return true;
        if (isBytes() && asBytes().length == 0) return true;
        if (isList() && asList().isEmpty()) return true;
        if (isString() && asString().equals("")) return true;

        return false;
    }

    public int length() {
        decode();
        if (isList()) {
            return asList().size();
        } else if (isBytes()) {
            return asBytes().length;
        } else if (isString()) {
            return asString().length();
        }
        return 0;
    }
}