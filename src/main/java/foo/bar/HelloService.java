package foo.bar;

import foo.bar.annotation.Fooish;
import org.springframework.stereotype.Component;

@Component
@Fooish(cool=false, tags= {"sixfoo"})
public class HelloService {
    public String sayHello() {
        return "Hello world!";
    }
}
