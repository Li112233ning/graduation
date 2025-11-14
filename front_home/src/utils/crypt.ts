import { JSEncrypt } from 'jsencrypt'

// 密钥对生成 http://web.chacuo.net/netrsakeypair
// RSA 公钥 对应的私钥放在后端项目的application-basic.yml文件下
const publicKey =
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCh6HkK+rCM37FAzCHVythTc6pxvr551K07CRhdX/NjCddHAuQMOd/57R5fiIwgVNEfCsD1cIyS6A8IWj4DtJLR2t29JehPpqiFSJ4hNtDcLNxNJiYRcCQvyMQeyQIPE5Ljc35c72YwDtQAsIJChsauyLrc+E6HC3gn1JDm18HNXwIDAQAB'

/**
 * RSA加密
 * @param txt 要加密的文本
 * @returns 加密后的Base64字符串
 */
export function rsaEncrypt(txt: string): string {
  if (!txt) {
    return ''
  }
  
  const encryptor = new JSEncrypt()
  // 设置公钥
  encryptor.setPublicKey(publicKey)
  // 对数据进行加密
  const encryptedValue = encryptor.encrypt(txt)

  // 检查加密是否成功
  if (typeof encryptedValue === 'boolean' || !encryptedValue) {
    throw new Error('RSA加密失败：加密返回值为空或布尔值')
  }

  return encryptedValue
}

