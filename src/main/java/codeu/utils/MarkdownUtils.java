//By: Nathalia Lie and Trisha Zalani
//
// This utility applies styles to texts.
// Implements parsing and sanitizing.
//
// Ex: Users could enter [**bold**] text which is parsed into [<b>bold</b>] text
// to render in the page

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Parses through input message.
 * Recognizes markdown symbols and returns message with HTML tags
 */
// IMPLEMENT HERE



/**
 * Takes care of sanitizing input.
 * Here, whitelist allows full range of text nodes, safe links, and image tags.
 * Requires: [message] is input untrusted HTML
 * Returns: safe HTML according to white-list of permitted tags and attributes.
 */
public String cleanMessage (String message){
  return Jsoup.clean(message, Whitelist.basicWithImages());
}
