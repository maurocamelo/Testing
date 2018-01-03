package mc.mockito;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegMatch extends TypeSafeMatcher<String> {

    private final String reg;

    public RegMatch(final String regex) {
        this.reg = regex;
    }

    public static RegMatch matchesRegex(final String regex) {
        return new RegMatch(regex);
    }
    
    @Override
    public void describeTo(final Description description) {
        description.appendText("matches =`" + reg + "`");
    }

    @Override
    public boolean matchesSafely(final String string) {
        return string.matches(reg);
    }

    
}
