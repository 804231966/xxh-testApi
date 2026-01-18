package org.xxhexample;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报文加密服务实现类
 */
public class DefaultMessageEncryptionService implements MessageEncryptionService {
    
    private static final int GCM_IV_LENGTH = 12; // 96 bits
    private static final int GCM_TAG_LENGTH = 16; // 128 bits
    
    private final String defaultKey;
    
    public DefaultMessageEncryptionService(String defaultKey) {
        this.defaultKey = defaultKey;
    }
    
    @Override
    public String encrypt(String originalMessage) throws EncryptionException {
        return encrypt(originalMessage, EncryptionAlgorithm.AES);
    }
    
    @Override
    public String encrypt(String originalMessage, EncryptionAlgorithm algorithm) throws EncryptionException {
        if (originalMessage == null || originalMessage.isEmpty()) {
            throw new EncryptionException("Original message cannot be null or empty");
        }
        
        try {
            switch (algorithm) {
                case AES:
                    return encryptWithAES(originalMessage);
                case RSA:
                    // RSA实现需要公钥/私钥对，这里简化处理
                    throw new UnsupportedOperationException("RSA encryption not implemented in this example");
                case DES:
                    // DES实现
                    throw new UnsupportedOperationException("DES encryption not implemented in this example");
                default:
                    throw new EncryptionException("Unsupported encryption algorithm: " + algorithm);
            }
        } catch (Exception e) {
            throw new EncryptionException("Encryption failed", e);
        }
    }
    
    @Override
    public List<String> encryptBatch(List<String> messages) throws EncryptionException {
        if (messages == null || messages.isEmpty()) {
            return List.of();
        }
        
        return messages.stream()
                .map(msg -> {
                    try {
                        return encrypt(msg);
                    } catch (EncryptionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 使用AES算法进行加密
     */
    private String encryptWithAES(String plainText) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(defaultKey);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        
        Cipher cipher = Cipher.getInstance(EncryptionAlgorithm.AES.getAlgorithm());
        
        // 生成随机IV
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes("UTF-8"));
        
        // 将IV和加密数据组合在一起
        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);
        
        return Base64.getEncoder().encodeToString(result);
    }
}
