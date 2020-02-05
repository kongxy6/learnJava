package io.nio;

import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

public class BatchOperation {

    /**
     * buffer 和大数组或小数组的交互
     * 批量操作位优先满足源目的数组的需求
     */
    @Test
    public void test() {
        String content = "我曾七次鄙视自己的灵魂:\n" +
                "第一次,当它本可进取时，却故作谦卑；\n" +
                "第二次,当它空虚时，用爱欲来填充；\n" +
                "第三次,在困难和容易之间，它选择了容易；\n" +
                "第四次,它犯了错，却借由别人也会犯错来宽慰自己；\n" +
                "第五次,它自由软弱，却把它认为是生命的坚韧；\n" +
                "第六次,当它鄙夷一张丑恶的嘴脸时，却不知那正是自己面具中的一副；\n" +
                "第七次,它侧身于生活的污泥中虽不甘心，却又畏首畏尾。\n";
        char[] chars = new char[content.length()];
        content.getChars(0, content.length(), chars, 0);
        // 处于可以取数据的状态
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        char[] bigArray = new char[1000];
        // 如果您想将一个小型缓冲区传入一个大型数组，您需要明确地指定缓冲区中剩余的数据长度
        charBuffer.get(bigArray, 0, charBuffer.remaining());

        // 将buffer的position重置
        charBuffer.rewind();

        char[] smallArray = new char[100];
        charBuffer.get(smallArray, 0, Math.min(smallArray.length - 0, charBuffer.remaining()));

        // 为buffer放数据做准备
        charBuffer.clear();
        charBuffer.put(bigArray, 0, Math.min(bigArray.length - 0, charBuffer.remaining()));

        System.out.println(charBuffer);
    }
}
