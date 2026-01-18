package org.xxhexample;

import org.xxhexample.DefaultMessageEncryptionService;
import org.xxhexample.EncryptionAlgorithm;
import org.xxhexample.EncryptionException;
import org.xxhexample.MessageEncryptionService;

import java.util.List;

public class EncryptionExample {
    
    public static void main(String[] args) {
        try {
            // 创建加密服务实例，传入Base64编码的密钥
            MessageEncryptionService encryptionService =
                new DefaultMessageEncryptionService("1234567890123456789012");
            
            // 加密单个报文
            String originalMessage = "This is a confidential message";
            String encryptedMessage = encryptionService.encrypt(originalMessage);
            System.out.println("Encrypted: " + encryptedMessage);
            
            // 指定算法加密
            String encryptedWithSpecificAlg = 
                encryptionService.encrypt(originalMessage, EncryptionAlgorithm.AES);
            System.out.println("Encrypted with AES: " + encryptedWithSpecificAlg);
            
            // 批量加密
            List<String> messages = List.of(
                "Message 1",
                "Message 2", 
                "Message 3"
            );
            List<String> encryptedMessages = encryptionService.encryptBatch(messages);
            System.out.println("Batch encrypted messages: " + encryptedMessages);
            
        } catch (EncryptionException e) {
            System.err.println("Encryption error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
