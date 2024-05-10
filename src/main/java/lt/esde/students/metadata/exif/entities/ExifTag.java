package lt.esde.students.metadata.exif.entities;

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
        this.group = group.trim();

        if (valuesNotes.trim().isEmpty()) {
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
