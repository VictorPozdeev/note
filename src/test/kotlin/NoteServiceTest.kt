import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @After
    fun clearAfterTest() {
        NoteService.clear()
    }

    @Test
    fun addNote() {
        val notes = NoteService
        val result = notes.add("First", "Note")
        assertEquals(1, result)
    }

    @Test
    fun deleteNote() {
        val notes = NoteService
        notes.add("1", "First")
        notes.add("2", "Second")

        val result = notes.delete(2)

        assertEquals(1, result)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun deleteNote_Removed() {
        val notes = NoteService
        notes.add("1", "First")
        notes.add("2", "Second")

        notes.delete(2)
        notes.delete(2)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun deleteNote_NotFound() {
        val notes = NoteService
        notes.add("1", "First")
        notes.add("2", "Second")

        notes.delete(100)
    }

    @Test
    fun editNote() {
        val notes = NoteService
        notes.add("1", "First")

        val result = notes.edit(1, "new 1", "First_new")

        assertEquals(1, result)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun editNote_Removed() {
        val notes = NoteService
        notes.add("1", "First")
        notes.add("2", "Second")
        notes.delete(2)
        notes.edit(2, "new 1", "First_new")
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun editNote_NotFound() {
        val notes = NoteService
        notes.add("1", "First")

        notes.edit(100, "new 1", "First_new")
    }

    @Test
    fun get_NotEmpty() {
        val notes = NoteService
        notes.add("1", "First")

        assertFalse(notes.get().isEmpty())
    }

    @Test
    fun get_Empty() {
        val notes = NoteService

        assertTrue(notes.get().isEmpty())
    }

    @Test
    fun getById() {
        val notes = NoteService
        notes.add("1", "First")

        assertFalse(notes.getById(1).isDeletedNote)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun getById_Removed() {
        val notes = NoteService
        notes.add("1", "First")
        notes.delete(1)

        assertFalse(notes.getById(1).isDeletedNote)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun getById_Null() {
        val notes = NoteService
        notes.add("1", "First")

        notes.getById(0)
    }

    @Test
    fun createComment() {
        val notes = NoteService
        notes.add("First", "Note")

        val result = notes.createComment(1, "First comment")

        assertEquals(1, result)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun createComment_RemovedNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.delete(1)
        notes.createComment(1, "First comment")
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun createComment_NoSuch() {
        val notes = NoteService

        notes.createComment(1, "First comment")
    }

    @Test
    fun deleteComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        val result = notes.deleteComment(1, 1)
        assertEquals(1, result)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteComment_RemovedNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.delete(1)

        notes.deleteComment(1, 1)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteComment_RemovedComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        notes.deleteComment(1, 1)
        notes.deleteComment(1, 1)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteComment_NoSuchComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        notes.deleteComment(1, 100)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteComment_NoSuchNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        notes.deleteComment(100, 1)
    }

    @Test
    fun editComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        val result = notes.editComment(1, 1, "First comment_new")

        assertEquals(1, result)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun editComment_RemovedComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.deleteComment(1, 1)

        notes.editComment(1, 1, "First comment_new")
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun editComment_RemovedNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.delete(1)

        notes.editComment(1, 1, "First comment_new")
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun editComment_NoSuchComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        notes.editComment(1, 100, "First comment_new")
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun editComment_NoSuchNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")

        notes.editComment(100, 1, "First comment_new")
    }

    @Test
    fun getComments() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.createComment(1, "Second comment")
        notes.deleteComment(1,1)

        assertFalse(notes.getComments(1).isEmpty())
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun getComments_NoSuch() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.createComment(1, "Second comment")
        notes.deleteComment(1,1)

        notes.getComments(100)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun getComments_RemovedNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.add("2", "Second")
        notes.delete(1)

        notes.getComments(1)
    }

    @Test
    fun getComments_RemovedComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.createComment(1, "Second comment")
        notes.deleteComment(1,1)
        notes.deleteComment(1,2)

        assertTrue(notes.getComments(1).isEmpty())
    }

    @Test
    fun restoreComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.deleteComment(1,1)
        val result = notes.restoreComment(1,1)
        assertEquals(1, result)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun restoreComment_NoSuchComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.deleteComment(1,1)
        notes.restoreComment(1,100)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun restoreComment_NoSuchNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.deleteComment(1,1)
        notes.restoreComment(100,1)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun restoreComment_RemovedComment() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.deleteComment(1,1)
        notes.restoreComment(100,1)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun restoreComment_RemovedNote() {
        val notes = NoteService
        notes.add("First", "Note")
        notes.createComment(1, "First comment")
        notes.delete(1)
        notes.restoreComment(1,1)
    }
}