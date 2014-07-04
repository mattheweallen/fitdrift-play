package controllers;

import play.libs.F;
import play.mvc.*;

import utils.ExceptionMailer;

public class CatchAction extends Action.Simple {
    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        try {
            return delegate.call(context);
        } catch (Throwable e) {
            ExceptionMailer.send(e);
            throw new RuntimeException(e);
        }
    }

}