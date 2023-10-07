import java.lang.RuntimeException

object NoteService {
    private var notes = emptyList<Note>()
    private var noteId = 1

    class NoteNotFoundException(message: String) : RuntimeException(message)
    class CommentNotFoundException(message: String) : RuntimeException(message)

    fun add(title: String, text: String): Int {
        val newNote = Note(id = noteId, title = title, text = text, comments = emptyList())
        notes += listOf(newNote)
        return noteId++
    }

    fun delete(noteId: Int): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                note.isDeletedNote = true
                return 1
            }
        }
        throw NoteNotFoundException("There is no such note")
    }

    fun edit(noteId: Int, title: String, text: String): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                note.isDeletedNote = true
                val updateNote = Note(id = noteId, title = title, text = text, comments = note.comments)
                notes += listOf(updateNote)
                return 1
            }
        }
        throw NoteNotFoundException("There is no such note")
    }

    fun get(): List<Note> = notes.filter { !it.isDeletedNote }

    fun getById(noteId: Int): Note {
        if (noteId == 0) throw NoteNotFoundException("There is no such note")
        val note = notes.filter { it.id == noteId && !it.isDeletedNote }
        if (note.isEmpty()) throw NoteNotFoundException("There is no such note")
        return note.first()
    }

    fun createComment(noteId: Int, message: String): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                if (note.comments.isEmpty()) {
                    note.comments += listOf(Comments(id = 1, text = message))
                    return 1
                } else {
                    val nextCommentId = note.comments.size + 1
                    note.comments += listOf(Comments(id = nextCommentId, text = message))
                    return 1
                }
            }
        }
        throw CommentNotFoundException("Comment not created")
    }

    fun deleteComment(noteId: Int, commentId: Int): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                for ((_, comment) in note.comments.withIndex()) {
                    if (comment.id == commentId && !comment.isDeletedComment) {
                        comment.isDeletedComment = true
                        return 1
                    }
                }
            }
        }
        throw CommentNotFoundException("There is no such comment")
    }

    fun editComment(noteId: Int, commentId: Int, message: String): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                for ((_, comment) in note.comments.withIndex()) {
                    if (comment.id == commentId && !comment.isDeletedComment) {
                        comment.id = 0
                        comment.isDeletedComment = true
                        val updateComment = Comments(id = commentId, text = message)
                        note.comments += listOf(updateComment)
                        return 1
                    }
                }
            }
        }
        throw CommentNotFoundException("There is no such comment")
    }

    fun getComments(noteId: Int): List<Comments> {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                return note.comments.filter { !it.isDeletedComment }
            }
        }
        throw CommentNotFoundException("There is no such comments")
    }

    fun restoreComment(noteId: Int, commentId: Int): Int {
        for ((_, note) in notes.withIndex()) {
            if (note.id == noteId && !note.isDeletedNote) {
                for ((_, comment) in note.comments.withIndex()) {
                    if (comment.id == commentId && comment.isDeletedComment) {
                        comment.isDeletedComment = false
                        return 1
                    }
                }
            }
        }
        throw CommentNotFoundException("There is no such comments")
    }

    override fun toString(): String {
        return notes.joinToString()
    }

    fun clear() {
        notes = emptyList()
        noteId = 1
    }
}