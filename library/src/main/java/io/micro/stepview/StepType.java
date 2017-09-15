package io.micro.stepview;

/**
 * View type definition.
 *
 * @author act262@gmail.com
 */
public @interface StepType {
    int START = 0;
    int NORMAL = 1;
    int END = 2;
    int ONLY_ONE = 3;
}
