package io.mattcarroll.androidtesting;

public interface ListensForBackgroundWork {
    /**
     * Called to indicate that work undetectable by Espresso is started
     */
    void onStartWork(String uniqueTag);

    /**
     * Called to indicate that work undetectable by Espresso is finish
     */
    void onFinishWork(String uniqueTag);
}
