package com.ffwatl.common.encryption;

import com.ffwatl.common.config.RuntimeEnvironmentKeyResolver;
import com.ffwatl.common.config.SystemPropertyRuntimeEnvironmentKeyResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The default encryption module simply passes through the decrypt and encrypt text.
 * A real implementation should adhere to PCI compliance, which requires robust key
 * management, including regular key rotation. An excellent solution would be a module
 * for interacting with the StrongKey solution. Refer to this discussion:
 *
 * http://www.strongauth.com/forum/index.php?topic=44.0
 *
 */
public class PassthroughEncryptionModule implements EncryptionModule{

    private RuntimeEnvironmentKeyResolver keyResolver = new SystemPropertyRuntimeEnvironmentKeyResolver();
    private static final Logger logger = LogManager.getLogger();

    public PassthroughEncryptionModule() {
        if ("production".equals(keyResolver.resolveRuntimeEnvironmentKey())) {
            logger.warn("This passthrough encryption module provides NO ENCRYPTION and should NOT be used in production.");
        }
    }

    @Override
    public String encrypt(String plainText) {
        return plainText;
    }

    @Override
    public String decrypt(String cipherText) {
        return cipherText;
    }
}