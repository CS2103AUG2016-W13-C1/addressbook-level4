package seedu.address.model.qualifiers;

import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;

public class NameQualifier implements Qualifier{
    private Set<String> nameKeyWords;

    public NameQualifier(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName + " " + task.getDetail().value, keyword))
                .findAny()
                .isPresent();
    }

    @Override
    public String toString() {
        return "name=" + String.join(", ", nameKeyWords);
    }
}
