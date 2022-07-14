public class AgentSource {
    public static void main(String[] args) {
        System.out.println("Author: yougar0x00");
        System.out.println("Github: https://github.com/yougar0/AntiDecAgent");
        System.out.println("Thx for your star.Happy day!");
        String str = JavaLauncherIdea.getThisJarPath();
        String path = str.substring(0, str.lastIndexOf(java.io.File.separator) + 1);
        String source = str.substring(str.lastIndexOf(java.io.File.separator) + 1);
        java.util.jar.Attributes attrs = new JavaLauncherIdea().getMainAttributes();
        String encrypted = attrs.getValue("Encrypted-Jars");
        if (encrypted != null && !encrypted.isEmpty()) {
            String[] encryptedJars = encrypted.split(" ");
            String[] var7 = encryptedJars;
            int var8 = encryptedJars.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String jarFile = var7[var9];
                java.io.InputStream is;
                try {
                    if (jarFile.equals(source)) {
                        is = this.getClass().getResourceAsStream("jar/" + source);
                    } else {
                        is = new java.io.FileInputStream(path + java.io.File.separator + jarFile);
                    }
                    javax.crypto.Cipher cipher = new JavaLauncherIdea().getCipher();
                    javax.crypto.CipherInputStream cis = new javax.crypto.CipherInputStream(is, cipher);
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

                    byte[] b = new byte[1024];
                    int numberOfBytedRead;
                    while ((numberOfBytedRead = cis.read(b)) >= 0) {
                        baos.write(b, 0, numberOfBytedRead);
                    }
                    String targetFile = source + "_unpacked.jar";
                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(targetFile);
                    fileOutputStream.write(baos.toByteArray());
                    fileOutputStream.close();
                    baos.close();
                    System.out.println("Successfully write jar file: " + targetFile);
                } catch (Exception e) {

                }
            }
        }
    }
}