package com.gagan.opengl.demo1.graphics.shapes;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by gagan on 4/28/17.
 */

public class Square {
    /**
     * Store our model data in a float buffer.
     */
    private final FloatBuffer vertexData;

    /**
     * Store the order to drawa the data in a Short buffer.
     */
    private final ShortBuffer drawListBuffer;
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

    public Square(int uMatrixHandle, int aPositionHandle, int aTextureHandle) {
        this.uMatrixHandle = uMatrixHandle;
        this.aPositionHandle = aPositionHandle;
        this.aTextureHandle = aTextureHandle;

        final float[] squareVerticesData = {
                // X, Y,
                0.0f,  1.4f,   // top left

                0.0f, 0.4f,   // bottom left

                1.0f, 0.4f,   // bottom right

                1.0f,  1.4f,  // top right
        };


// Initialize the buffers.
        vertexData  = ByteBuffer.allocateDirect(squareVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(squareVerticesData);
//        vertexData.position(0);


        final short drawOrder[] = {0,1,2,0,2,3};

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

    }




    public void draw(float[] mvpMatrix){

        glUniformMatrix4fv(uMatrixHandle, 1, false, mvpMatrix, 0);

        vertexData.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexData);

        glEnableVertexAttribArray(aPositionHandle);

        // Pass in the color information
//        vertexData.position(mColorOffset);
//        glVertexAttribPointer(aColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
//                mStrideBytes, vertexData);
//
//        glEnableVertexAttribArray(aColorHandle);


        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, 6,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

    }
}
