# WikipediaSearchEngine
Semantic based search engine using Wikipedia dumps.
Data Set- https://dumps.wikimedia.org/enwiki/20170220/
Code walk through Demo: https://youtu.be/eWeX0JO9He0

The search engine on Wikipedia first matches users’ queries with the content of
Wikipedia pages using keyword match by Vector Space Model and relevancy by Cosine Similarity. If there is no document with the same subject as the query, the system
will return documents with content which has matching keywords

This search engine  not only matches the content with the
user’s inputs but also need to find documents with similar semantic meanings to determine
relevance.

Indexing is done to parse and store unstructured data to facilitate fast and accurate Information Retrieval.
Aopted 2 different data structures for Indexing.
#Inverted Index
  It stores term frequency of each term, later converted to TFIDF weight.
  It’s used by Vector Space Model. 
#Term Document Matrix
  It stores occurrences of a word in documents in a 2- dimensional sparse matrix.
  It’s used in Latent Semantic Analysis.
  
# Vevtor Space Model 
 This model considers each term as a axis in a multidimensional space and a point as a Document.
  Each term is assigned a tf-idf weight.
  Application :
    Keyword match retrieval search.
    Ranks retrieved documents based on Cosine Similarity.
# Latent Semantic Analysis 
  Term-Document Patterns:
    co-occurrences of terms in different documents to determine the relevancy 
