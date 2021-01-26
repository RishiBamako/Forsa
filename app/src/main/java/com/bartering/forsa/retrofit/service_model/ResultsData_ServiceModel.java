package com.bartering.forsa.retrofit.service_model;

import androidx.databinding.BaseObservable;

import java.util.List;

public class ResultsData_ServiceModel extends BaseObservable {


    /**
     * results : {"books":[{"author":"Ken Follett","description":"In a prequel to \u201cThe Pillars of the Earth,\u201d a boatbuilder, a Norman noblewoman and a monk live in England under attack by the Welsh and the Vikings.","book_image":"https://s1.nyt.com/du/books/images/9780525954989.jpg","title":"THE EVENING AND THE MORNING","price":"0","publisher":"Viking","rank":"1"}]}
     */

    private ResultsBean results;

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        private List<BooksBean> books;

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class BooksBean {
            /**
             * author : Ken Follett
             * description : In a prequel to “The Pillars of the Earth,” a boatbuilder, a Norman noblewoman and a monk live in England under attack by the Welsh and the Vikings.
             * book_image : https://s1.nyt.com/du/books/images/9780525954989.jpg
             * title : THE EVENING AND THE MORNING
             * price : 0
             * publisher : Viking
             * rank : 1
             */

            private String author;
            private String description;
            private String book_image;
            private String title;
            private String price;
            private String publisher;
            private String rank;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getBook_image() {
                return book_image;
            }

            public void setBook_image(String book_image) {
                this.book_image = book_image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }
        }
    }
}


