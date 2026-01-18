package org.xxhexample;

/**
 * 支持的加密算法枚举
 */
public enum EncryptionAlgorithm {
    AES("AES/GCM/NoPadding"),
    RSA("RSA/ECB/PKCS1Padding"),
    DES("DES/CBC/PKCS5Padding");
    
    private final String algorithm;
    
    EncryptionAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
}
