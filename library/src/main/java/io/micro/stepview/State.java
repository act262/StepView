package io.micro.stepview;

/**
 * View state definition.
 *
 * @author act262@gmail.com
 */
public @interface State {
    int INACTIVE = 0x001;
    int ACTIVE = 0x010;
    int COMPLETED = 0x100;
}
