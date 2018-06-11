/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aru.newsAggregator;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

/**
 * Building the queries
 *
 * @author Nikoletta Szedljak
 */
public class QueryBuilding {

    /**
     * Create String array for tab2 to list all article titles
     *
     * @return
     */
    protected static String[] createArticleTitleList() {

        QueryExecution qexec = generateQueryExecution(Queries.listArticleTitlesForTab2());
        String[] properties = {"ArticleTitles"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);

        String[] strArr = new String[arrList.size()];
        strArr = arrList.toArray(strArr);

        return strArr;
    }

    /**
     * Create a formatted String for presenting an Article on tab2
     *
     * @param selectedArticle
     * @return
     */
    protected static String createPresentSelectedArticle(String selectedArticle) {

        QueryExecution qexec = generateQueryExecution(Queries.presentSelectedArticle(selectedArticle));
        String[] properties = {"ArticleTitle", "ArticleDate", "ReporterName", "New", "Topics", "Cluster",
            "Company", "ArticleText"};

        return generateFormattedStringForTab2(qexec, properties);

    }

    /**
     * Comments list for tab3
     *
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     * @return formatted String with the query result
     */
    protected static String createCommentsList(String selectedTopic, String selectedCluster, String selectedCompany, String selectedRadioButton, String searchedAfterDate) {

        QueryExecution qexec = generateQueryExecution(Queries.listCommentsForTab3(selectedTopic, selectedCluster, selectedCompany, selectedRadioButton, searchedAfterDate));
        String[] properties = {"ArticleTitle", "New", "Topics", "Cluster",
            "Company", "Comments", "CommentDate"};

        return generateFormattedStringForTab3(qexec, properties);

    }

    /**
     * List articles for tab4
     *
     * @param selectedCategory - value of the jComboBox for Category
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     * @return formatted String with the query result
     */
    protected static String createArticlesList(String selectedCategory, String selectedTopic, String selectedCompany, String selectedCluster, String selectedRadioButton, String searchedAfterDate) {

        QueryExecution qexec = generateQueryExecution(Queries.listArticlesForTab4(selectedCategory, selectedTopic, selectedCompany, selectedCluster, selectedRadioButton, searchedAfterDate));
        String[] properties = {"Reporters", "New", "Company", "ArticleTitle", "Topics", "Cluster", "ArticleDate"};

        return generateFormattedStringForTab4(qexec, properties);

    }

    /**
     * List articles for tab5
     *
     * @param searchedWord - value of the searched word from
     * jTextField_ForRelatedArticles
     * @param isHeadlineSelected - value of jCheckBox_HeadlineForRelatedArticles
     * @param isArticleTextSelected - value of
     * jCheckBox_ArticleTextForRelatedArticles
     * @param isCommentsSelected - value of jCheckBox_CommentsForRelatedArticles
     * @param isArticleTitleSelected - value of
     * jCheckBox_ArticleTitleForRelatedArticles
     * @return
     */
    protected static String[] createArtTitles(String searchedWord, boolean isHeadlineSelected, boolean isArticleTextSelected, boolean isCommentsSelected, boolean isArticleTitleSelected) {

        QueryExecution qexec = generateQueryExecution(Queries.listArticleTitlesForTab5(searchedWord, isHeadlineSelected, isArticleTextSelected, isCommentsSelected, isArticleTitleSelected));
        String[] properties = {"ArticleTitle"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);

        String[] strArr = new String[arrList.size()];
        strArr = arrList.toArray(strArr);

        return strArr;
    }

    /**
     * list related article titles for tab5 - for selected article
     *
     * @param selectedArticle
     * @return
     */
    protected static String createRelatedeArticleTitles(String selectedArticle) {

        QueryExecution qexec = generateQueryExecution(Queries.listRelatedArticles(selectedArticle));
        String[] properties = {"RelatedArticleTitle"};

        return generateFormattedStringForTab5(qexec, properties);

    }

    /**
     * Creates String for jComboBox
     *
     * @return String ArrayList with the list of Topics
     */
    protected static ArrayList<String> createTopicsList() {

        QueryExecution qexec = generateQueryExecution(Queries.listTopics());
        String[] properties = {"Topics"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);
        arrList.add(0, "Topic");

        return arrList;
    }

    /**
     * Creates String for jComboBox
     *
     * @return String ArrayList with the list of Clusters
     */
    protected static ArrayList<String> createClustersList() {

        QueryExecution qexec = generateQueryExecution(Queries.listClusters());
        String[] properties = {"Clusters"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);
        arrList.add(0, "Cluster");

        return arrList;
    }

    /**
     * Creates String for jComboBox
     *
     * @return String ArrayList with the list of Companies
     */
    protected static ArrayList<String> createCompaniesList() {

        QueryExecution qexec = generateQueryExecution(Queries.listCompanies());
        String[] properties = {"Company"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);
        arrList.add(0, "Company");

        return arrList;
    }

    /**
     * Creates String for jComboBox
     *
     * @return String ArrayList with the list of Categories
     */
    public static ArrayList<String> createCategoryList() {

        QueryExecution qexec = generateQueryExecution(Queries.listCategories());
        String[] properties = {"Categories"};

        ArrayList<String> arrList;
        arrList = generateResultStringArrayList(qexec, properties);
        arrList.add(0, "Category");

        return arrList;
    }

    /**
     * Creates a Query execution object from the query String
     *
     * @param queryString - contains the text for the query
     * @return QueryExecution object
     */
    private static QueryExecution generateQueryExecution(String queryString) {

        Query query = QueryFactory.create(queryString);

        // Remote execution.
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:8171/News/query", query);
        // Set the DBpedia specific timeout.
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

        return qexec;

    }

    /**
     * Creates an ArrayList with formatted String for the result
     *
     * @param qexec
     * @param property
     * @return
     */
    private static ArrayList<String> generateResultStringArrayList(QueryExecution qexec, String[] property) {

        ResultSet rs = qexec.execSelect();

        ArrayList<String> arrList = new ArrayList<>();

        while (rs.hasNext()) {

            QuerySolution sol = rs.nextSolution();

            for (String s : property) {
                if (sol.get(s) == null) {
                    continue;
                }
                arrList.add(sol.get(s).toString());
            }

        }

        return arrList;
    }

    /**
     * Formatting tab2 presented article
     *
     * @param qexec
     * @param property {"ArticleTitle" "ArticleDate", "ReporterName", "New",
     * "Topics", "Cluster", "Company", "ArticleText",}
     * @return
     */
    private static String generateFormattedStringForTab2(QueryExecution qexec, String[] property) {

        StringBuilder result = new StringBuilder();

        ResultSet rs = qexec.execSelect();
        QuerySolution sol = rs.nextSolution();

        if (sol.get(property[0]) == null) {
            return "There are no results for this search.\nTry another filter.";
        }

        result.append("<div>").append("<h1>").append(sol.get(property[0])).append("</h1>");

        result.append((sol.get(property[1])).toString().replace('T', ' '));

        result.append("<h2>").append(sol.get(property[2])).append("</h2>");

        result.append("<b>Category: </b>").append(sol.get(property[3])).append("<br>");

        result.append("<b>Topic: </b>").append(sol.get(property[4])).append("<br>");

        if (sol.get(property[5]) != null) {
            result.append("<b>Cluster: </b>").append(sol.get(property[5])).append("<br>");
        }

        result.append("<b>Company: </b>").append(sol.get(property[6])).append("<br>");

        result.append("<div class=\"secondDiv\">");
        result.append((sol.get(property[7])).toString().replaceAll("\n", "<br>"));
        result.append("</div>").append("</div>");

        return result.toString();
    }

    /**
     * Formatting tab3 presented article
     *
     * @param qexec
     * @param property {"ArticleTitle", "New", "Topics", "Cluster", "Company",
     * "Comments", "CommentDate"}
     * @return formatted String with the result of the query
     */
    private static String generateFormattedStringForTab3(QueryExecution qexec, String[] property) {

        StringBuilder result = new StringBuilder();
        String[] arrForComments;
        String[] arrForCommentDates;

        ResultSet rs = qexec.execSelect();
        while (rs.hasNext()) {
            QuerySolution sol = rs.nextSolution();

            //result.append("<div class=\"secondDiv\">");
            if (sol.get(property[0]) == null) {
                return "There are no results for this search.\nTry another filter.";
            }

            result.append("<div><hr>");

            result.append("<h1>").append(sol.get(property[0])).append("</h1>");

            result.append("<h2>Category: </h2>").append("<h3>").append(sol.get(property[1])).append("</h3>");

            result.append("<h2>Topic: </h2>").append("<h3>").append(sol.get(property[2])).append("</h3>");

            if (sol.get(property[3]) != null) {
                result.append("<h2>Cluster: </h2>").append("<h3>").append(sol.get(property[3])).append("</h3>");
            }

            result.append("<h2>Company: </h2>").append("<h3>").append(sol.get(property[4])).append("</h3>");

            arrForComments = (sol.get(property[5])).toString().split("<n>");
            arrForCommentDates = (sol.get(property[6])).toString().split("<n>");
            result.append("<h2>Comments</h2>");
            for (int i = 0; i < arrForComments.length; i++) {
                result.append("<h3>").append(i + 1).append(". ").append(arrForComments[i]).append("</h3>")
                        .append("<h3>").append(arrForCommentDates[i].replace("T", " ")).append("</h3>");
            }

            result.append("<hr></div>");
        }

        return result.toString();
    }

    /**
     * Formatting tab4 presented article
     *
     * @param qexec
     * @param property {"Reporters", "New", "Company", "ArticleTitle*",
     * "Topics*", "Cluster*", "ArticleDate*"}
     * @return
     */
    private static String generateFormattedStringForTab4(QueryExecution qexec, String[] property) {

        StringBuilder result = new StringBuilder();
        String[] arrForArticleTitles;
        String[] arrForArticleDates;

        ResultSet rs = qexec.execSelect();
        while (rs.hasNext()) {
            QuerySolution sol = rs.nextSolution();

            if (sol.get(property[0]) == null) {
                return "There are no results for this search.\nTry another filter.";
            }

            result.append("<div><hr>");

            result.append("<h1>").append(sol.get(property[0])).append("</h1>");

            result.append("<h2>Expert in Category: </h2>").append("<h3>").append(sol.get(property[1])).append("</h3>");

            result.append("<h2>Company: </h2>").append("<h3>").append(sol.get(property[2])).append("</h3>");

            result.append("<h2>Topics: </h2>").append("<h3>").append(sol.get(property[4])).append("</h3>");

            if (sol.get(property[5]) != null) {
                result.append("<h2>Clusters: </h2>").append("<h3>").append(sol.get(property[5])).append("</h3>");
            }

            arrForArticleTitles = (sol.get(property[3])).toString().split("<n>");

            arrForArticleDates = (sol.get(property[6])).toString().split("<n>");

            result.append("<h2>Articles</h2>");
            for (int i = 0; i < arrForArticleTitles.length; i++) {
                result.append("<h3>").append(i + 1).append(". ").append(arrForArticleTitles[i]).append("<br>")
                        .append(arrForArticleDates[i].replace("T", " ")).append("</h3>");
            }

            result.append("<hr></div>");
        }

        return result.toString();
    }

    /**
     * Formatting tab5 listing articles
     *
     * @param qexec
     * @param property {"RelatedArticleTitle"}
     * @return
     */
    private static String generateFormattedStringForTab5(QueryExecution qexec, String[] property) {

        StringBuilder result = new StringBuilder();

        ResultSet rs = qexec.execSelect();

        while (rs.hasNext()) {

            QuerySolution sol = rs.nextSolution();

            result.append("<div><h1>");

            for (String s : property) {
                if (sol.get(s) == null) {
                    continue;
                }
                result.append(sol.get(s).toString()).append("<hr>");
            }

            result.append("</h1></div>");
        }

        if ("<div><h1></h1></div>".equals(result.toString())) {
            return "There are no results for this search.\nTry another filter.";
        }

        return result.toString();
    }

}
