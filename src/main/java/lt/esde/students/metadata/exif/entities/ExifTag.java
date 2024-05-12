package lt.esde.students.metadata.exif.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExifTag {
    private final int id;
    private final String name;
    private final String writable;
    private final String group;
    private final String valuesNotes;
    private String value;

    public ExifTag(int id, String name, String writable, String group, String valuesNotes) {
        this.id = id;
        this.name = name.trim();
        this.writable = writable.trim();

        group = group.trim();
        if (group.equals("-")) {
            group = "";
        } else if (group.startsWith("-")) {
            Pattern groupPattern = Pattern.compile("-+(.\\w+)");
            Matcher groupMatcher = groupPattern.matcher(group);
            if (groupMatcher.find()) {
                group = groupMatcher.group(1);
            }
        }
        this.group = group;

        if (valuesNotes.replace("\u00a0","").trim().isEmpty()) {
            this.valuesNotes = "";
        } else {
            String[] valuesNotesSplit = valuesNotes.split("\n");
            StringBuilder sb = new StringBuilder();
            for (String s : valuesNotesSplit) {
                sb.append(s.replace("\n", "").trim());
                sb.append("\n");
            }
            this.valuesNotes = sb.toString();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWritable() {
        return writable;
    }

    public String getGroup() {
        return group;
    }

    public String getValuesNotes() {
        return valuesNotes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append(" (").append(name).append(")\n");
        sb.append("Writable: ").append(writable).append("\n");
        sb.append("Group: ").append(group).append("\n");
        sb.append("ValuesNotes: ").append("\n").append(valuesNotes);

        return sb.toString().trim();
    }
}
