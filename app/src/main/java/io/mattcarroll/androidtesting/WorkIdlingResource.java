package io.mattcarroll.androidtesting;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * IdlingResource that allows calling onFinishWork multiple times
 * when all works/jobs have unique name
 *
 * WARNING: When implementing IdlingResource, make sure your implementation is thread-safe
 */
public class WorkIdlingResource implements IdlingResource {
    // Final makes access to the fields partially thread-safe:
    // other threads will observe state of these fields at the moment right after contruction
    //   or LATER
    // Thus final fields containing values of primitive types (like int or boolean) and
    //   values of immutable types (like String) are thread-safe
    private final String name;

    // But an instance of HashSet is not an immutable object, so we need additional synchronization
    private final Set<String> currentJobs = Collections.synchronizedSet(new HashSet<String>());

    // IdlingResource implementations support only one resourceCallback
    // volatile guarantees that resourceCallback value (reference to ResourceCallback) is always
    //   synchronised between different threads
    private volatile ResourceCallback resourceCallback;

    public WorkIdlingResource(String name) {
        this.name = name;
    }

    public void onStartWork(String uniqueTag) {
        if (!currentJobs.add(uniqueTag)) { // `add` returns false if uniqueTag already exists
            throw new IllegalStateException("There is already running work with tag " + uniqueTag);
        }
    }

    public void onFinishWork(String uniqueTag) {
        // There is multiple calls to `currentJobs` methods, so make it is not changed between the calls
        synchronized (currentJobs) {
            if (!currentJobs.remove(uniqueTag)) {
                Log.w("AndroidTest", "Called onFinishWork with not running work " + uniqueTag);
            }

            // guard against calling registerIdleTransitionCallback with null argument during
            //   this method execution
            ResourceCallback resourceCallback = this.resourceCallback;
            if (currentJobs.isEmpty() && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        }
    }

    @Override
    public String getName() {
        // accessing immutable final field => thread-safe
        return this.name;
    }

    @Override
    public boolean isIdleNow() {
        // accessing final field with reference to thread-safe object => thread-safe
        return currentJobs.isEmpty();
    }

    // Because IdlingResource implementations can keep reference to only one resourceCallback
    //   you MUST NOT call this method from your tests. Otherwise you will overwrite callback set
    //   by Espresso and break waiting for idle.
    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        // changing volatile field => reference itself is always correctly observed by different
        //   threads.
        // BUT referenced object may be or may be not a thread-safe itself
        this.resourceCallback = resourceCallback;
    }
}
