package org.xxhexample;

import java.util.List;

/**
 * 报文加密接口
 */
public interface MessageEncryptionService {
    
    /**
     * 对原始报文进行加密
     * 
     * @param originalMessage 原始报文
     * @return 加密后的报文
     * @throws EncryptionException 加密异常
     */
    String encrypt(String originalMessage) throws EncryptionException;
    
    /**
     * 对原始报文进行加密，支持指定加密算法
     * 
     * @param originalMessage 原始报文
     * @param algorithm 加密算法类型
     * @return 加密后的报文
     * @throws EncryptionException 加密异常
     */
    String encrypt(String originalMessage, EncryptionAlgorithm algorithm) throws EncryptionException;
    
    /**
     * 批量加密报文
     * 
     * @param messages 要加密的报文列表
     * @return 加密后的报文列表
     * @throws EncryptionException 加密异常
     */
    List<String> encryptBatch(List<String> messages) throws EncryptionException;
}
