package io.mattcarroll.androidtesting;

public interface ListensForBackgroundWork {
    /**
     * Called to indicate that work undetectable by Espresso is started.
     * {@link #onFinishWork()} must be called once the work is finished.
     */
    void onStartWork();

    /**
     * Called to indicate that work undetectable by Espresso is finish.
     * Must be called exactly once for each {@link #onStartWork()} call
     */
    void onFinishWork();
}
