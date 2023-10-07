class Comments (
    var id: Int = 0,
    var fromId: Int = 0,
    var date: Int = 0,
    var text: String = "",
    var replyToUser: Int = 0,
    var replyToComment: Int = 0,
    //var attachment: Attachment? = null,
    var isDeletedComment: Boolean = false
) {
    override fun toString(): String {
        return "commentId = $id, text = \"$text\", isDeletedComment = $isDeletedComment"
    }
}