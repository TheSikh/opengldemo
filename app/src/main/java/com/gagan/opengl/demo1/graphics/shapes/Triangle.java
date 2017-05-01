package com.gagan.opengl.demo1.graphics.shapes;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by gagan on 4/28/17.
 */

public class Triangle {
    /**
     * Store our model data in a float buffer.
     */
    private final FloatBuffer triangleData;
    private final FloatBuffer triangleTextureData;
    /**
     * How many bytes per float.
     */
    private final int mBytesPerFloat = 4;

//    /** How many elements per vertex. */
//    private final int mStrideBytes = 2 * mBytesPerFloat;
//
//    /** Offset of the position data. */
//    private final int mPositionOffset = 0;
//
//    /** Size of the position data in elements. */
//    private final int mPositionDataSize = 2;

//    /** Offset of the color data. */
//    private final int mColorOffset = 3;
//
//    /** Size of the color data in elements. */
//    private final int mColorDataSize = 4;

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
//    private float[] mModelMatrix = new float[16];
    private final int uMatrixHandle;
    private final int aTextureHandle;
    private final int aPositionHandle;

    public Triangle(int uMatrixHandle, int aPositionHandle, int aTextureHandle) {
        this.uMatrixHandle = uMatrixHandle;
        this.aPositionHandle = aPositionHandle;
        this.aTextureHandle = aTextureHandle;
        // This triangle is red, green, and blue.
        final float[] verticesData = {
                // X, Y, Z
                -1.0f, 0.4f,

                0.0f, 0.4f,

                -0.50f, 1.4f,
        };
        // Initialize the buffers.
        triangleData = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(verticesData);
//        vertexData.position(0);
//
        final float[] textureData = {
                // X, Y, Z,
                // R, G, B, A
                -1.0f, 0.4f,

                0.0f, 0.4f,

                -0.50f, 1.4f,
        };
//        // Initialize the buffers.
        triangleTextureData = ByteBuffer.allocateDirect(textureData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(textureData);

//        textureData.rewind();


    }




    public void draw(float[] mvpMatrix){

//        glUniformMatrix4fv(uMatrixHandle, 1, false, mvpMatrix, 0);
//
//        triangleData.position(0);
//        glVertexAttribPointer(aPositionHandle, 2, GL_FLOAT, false,
//                2*4, triangleData);
//        glEnableVertexAttribArray(aPositionHandle);
//
//        // Pass in the color information
////        vertexData.position(mColorOffset);
////        glVertexAttribPointer(aColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
////                mStrideBytes, vertexData);
////
////        glEnableVertexAttribArray(aColorHandle);
//
//
//        glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        // This triangle is red, green, and blue.
        final float[] verticesData = {
                // X, Y, Z
                -1.0f, 0.4f,

                0.0f, 0.4f,

                -0.50f, 1.4f,
        };
        // Initialize the buffers.
        FloatBuffer triangleData = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(verticesData);
        glUniformMatrix4fv(uMatrixHandle, 1, false, mvpMatrix, 0);

        triangleData.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, triangleData);
        glEnableVertexAttribArray(aPositionHandle);

        // Pass in the color information
//        vertexData.position(mColorOffset);
//        glVertexAttribPointer(aColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
//                mStrideBytes, vertexData);
//
//        glEnableVertexAttribArray(aTextureHandle);


        glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

    }
}
