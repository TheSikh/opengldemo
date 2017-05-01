package com.gagan.opengl.demo1.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by gagan on 4/26/17.
 */

public class CustomGLES2View extends GLSurfaceView {

    private Renderer renderer;
    public CustomGLES2View(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer =  createRenderer();
        setRenderer(renderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);



    }



    private Renderer createRenderer(){
//        return new ShapeRenderer();
        return new SimpleRenderer(getContext());
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestRender();
    }
}
