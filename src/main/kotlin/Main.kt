fun main() {
    val service = NoteService

    val result = service.add("1 note", "data in the note")
    println(result)
    service.createComment(1, "1 comment for 1 note")
    service.createComment(1, "2 comment for 1 note")
    service.createComment(1, "3 comment for 1 note")
    println(service.getById(1))

    service.add("2 note", "data in the note 2")
    service.createComment(2, "1 comment for 2 note")
    service.createComment(2, "2 comment for 2 note")
    service.createComment(2, "3 comment for 2 note")
    println(service.getById(2))

    //println(service.toString())
    service.delete(2)
    service.edit(1, "1 note edit", "editable data in the note 1")
    service.deleteComment(1, 2)
    //println(service.get().toString())

    service.add("3 note", "data in the note 3")
    service.createComment(3, "1 comment for 3 note")
    service.createComment(3, "2 comment for 3 note")
    service.editComment(3, 2, "editable comment")

    //println(service.get().toString())

    //println(service.getComments(1))
    service.restoreComment(1, 2)
    service.deleteComment(1,1)
    service.deleteComment(1,2)
    service.deleteComment(1,3)
    println(service.getComments(1))

    //println(service.getById(1))


}

