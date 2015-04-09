package Utilities; // 09 Apr, 12:45 PM

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyTexturePacker {

    public static void main(String args[]) {
        TexturePacker.process("android/assets/in", "android/assets/out", "tileset");
    }

}
