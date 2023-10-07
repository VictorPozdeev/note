//sealed class Attachment(val type: String)

//class NoteAttachment(val note: Note) : Attachment("note")

class Note(
    var id: Int = 0,
    var ownerId: Int = 0,
    var title: String = "",
    var text: String = "",
    var comments: List<Comments>,
    var date: Int = 0,
    var readComments: Int = 0,
    var viewurl: String = "",
    var isDeletedNote: Boolean = false
) {
    override fun toString(): String {
        return "noteId = $id, title = \"$title\", text = \"$text\",  comments =\n $comments, isDeletedNote = $isDeletedNote\n"
    }
}
