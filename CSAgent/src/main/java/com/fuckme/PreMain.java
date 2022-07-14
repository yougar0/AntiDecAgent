package com.fuckme;

import javassist.*;
import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
public class PreMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new CobaltStrikeTransformer(), true);
    }

    static class CobaltStrikeTransformer implements ClassFileTransformer {
        private final ClassPool classPool = ClassPool.getDefault();

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            try {
                if (className.equals("JavaLauncherIdea$Decoder")) {
                    CtClass cls = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                    CtMethod mtd = cls.getDeclaredMethod("loadJars");
                    mtd.setBody("{\n" +
                            "        System.out.println(\"Author: yougar0x00\");\n" +
                            "        System.out.println(\"Github: https://github.com/yougar0/AntiDecAgent\");\n" +
                            "        System.out.println(\"Thx for your star.Happy day!\");\n" +
                            "        String str = JavaLauncherIdea.getThisJarPath();\n" +
                            "        String path = str.substring(0, str.lastIndexOf(java.io.File.separator) + 1);\n" +
                            "        String source = str.substring(str.lastIndexOf(java.io.File.separator) + 1);\n" +
                            "        java.util.jar.Attributes attrs = new JavaLauncherIdea().getMainAttributes();\n" +
                            "        String encrypted = attrs.getValue(\"Encrypted-Jars\");\n" +
                            "        if (encrypted != null && !encrypted.isEmpty()) {\n" +
                            "            String[] encryptedJars = encrypted.split(\" \");\n" +
                            "            String[] var7 = encryptedJars;\n" +
                            "            int var8 = encryptedJars.length;\n" +
                            "\n" +
                            "            for(int var9 = 0; var9 < var8; ++var9) {\n" +
                            "                String jarFile = var7[var9];\n" +
                            "                java.io.InputStream is;\n" +
                            "                try {\n" +
                            "                    if (jarFile.equals(source)) {\n" +
                            "                        is = this.getClass().getResourceAsStream(\"jar/\" + source);\n" +
                            "                    } else {\n" +
                            "                        is = new java.io.FileInputStream(path + java.io.File.separator + jarFile);\n" +
                            "                    }\n" +
                            "                    javax.crypto.Cipher cipher = new JavaLauncherIdea().getCipher();\n" +
                            "                    javax.crypto.CipherInputStream cis = new javax.crypto.CipherInputStream(is, cipher);\n" +
                            "                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();\n" +
                            "\n" +
                            "                    byte[] b = new byte[1024];\n" +
                            "                    int numberOfBytedRead;\n" +
                            "                    while ((numberOfBytedRead = cis.read(b)) >= 0) {\n" +
                            "                        baos.write(b, 0, numberOfBytedRead);\n" +
                            "                    }\n" +
                            "                    String targetFile = source + \"_unpacked.jar\";\n" +
                            "                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(targetFile);\n" +
                            "                    fileOutputStream.write(baos.toByteArray());\n" +
                            "                    fileOutputStream.close();\n" +
                            "                    baos.close();\n" +
                            "                    System.out.println(\"Successfully write jar file: \" + targetFile);\n" +
                            "                } catch (Exception e) {\n" +
                            "\n" +
                            "                }\n" +
                            "            }\n" +
                            "        }\n" +
                            "    }");
                    return cls.toBytecode();
                }
            } catch (Exception e) {

            }
            return classfileBuffer;
        }
    }
}