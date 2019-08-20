fun main() {

}

class Token {}

class Item {}

class Post()

fun submitPost(token: Token, item: Item): Post {
    val post = Post()
    return post
}

fun processPost(post: Post) {}

fun postItem(item: Item) {
    val token = preparePost()
    val post = submitPost(token, item)
    processPost(post)
}

fun preparePost(): Token {
    // makes a request and consequently blocks the main thread

    val token = Token()
    return token
}