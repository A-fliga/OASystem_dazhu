package org.oasystem_dazhu.utils;

import org.oasystem_dazhu.constants.Constants;

import java.io.File;

/**
 * Created by www on 2019/1/20.
 */

public class FileUtil {
    public static void deleteFile(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        } else if (file.exists()) {
            file.delete();
        }
    }

    public static void clearCache(){
        File file = new File(Constants.FILE_PARENT_DIR);
        deleteFile(file);
    }
}
