/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aru.newsAggregator;

/**
 * Define SPARQL queries
 *
 * @author Nikoletta Szedljak
 */
public class Queries {

    /**
     * Constant for setting the prefixes for the SPARQL queries
     */
    private static String PREFIX = "PREFIX news:<http://www.semanticweb.org/ns921/ontologies/2017/9/untitled-ontology-7#>\n"
            + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";

    /**
     * Lists all article titles for tab2
     *
     * @return query String
     */
    protected static String listArticleTitlesForTab2() {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append("SELECT (?ArticleTitle as ?ArticleTitles) ")
                .append("	WHERE { ?Article a news:Article ; ")
                .append("                   news:Article_Title ?ArticleTitle . } ")
                .append("ORDER BY ASC(?ArticleTitle)");

        return queryStr.toString();

    }

    /**
     * Presents selected article for tab2, tab5
     *
     * @param selectedArticle
     * @return query String
     */
    protected static String presentSelectedArticle(String selectedArticle) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append(" SELECT (GROUP_CONCAT(DISTINCT ?ArticleT) as ?ArticleTitle) ")
                .append("         (GROUP_CONCAT(DISTINCT ?ArtDate) as ?ArticleDate) ")
                .append("         (GROUP_CONCAT(DISTINCT ?RepName) as ?ReporterName) ")
                .append("         (GROUP_CONCAT(DISTINCT ?NewsCategories) as ?New) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Topic; SEPARATOR=\", \") as ?Topics) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Clust) as ?Cluster) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Comp) as ?Company) ")
                .append("         (GROUP_CONCAT(DISTINCT ?ArticleTex) as ?ArticleText) ")
                .append("	WHERE { ?NC	rdfs:subClassOf*	?parent .\n")
                .append("  		?NC	news:NewsCategory_Name	?NewsCategories .\n")
                .append("               ?Article a news:Article ;\n")
                .append("                   news:Article_Title ?ArticleT ;\n")
                .append("                   news:Article_Text ?ArticleTex ;\n")
                .append("                   news:Article_Date ?ArtDate ;")
                .append("                   news:is_in_news_category ?NC ;\n")
                .append("                   news:is_about_topic ?Topicc ;\n")
                .append("                   news:has_reporter ?Rep .\n")
                .append("  		?Rep news:works_at ?comp ;\n")
                .append("                   news:Reporter_Name ?RepName .\n")
                .append("  		?comp news:NewsCompany_Name ?Comp .\n")
                .append("               ?Topicc news:Topic_Name ?Topic .\n")
                .append("		OPTIONAL {?Article news:has_cluster ?Cl .}\n")
                .append("		OPTIONAL {?Cl news:Cluster_Name ?Clust . FILTER( !bound( ?Cl ) ) }\n")
                .append("       FILTER(regex(str(?ArticleT), \"").append(selectedArticle).append("\", \"i\"))\n")
                .append("       }\n");

        return queryStr.toString();

    }

    /**
     * List Comments for tab3
     *
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     * @return query text as a String
     */
    protected static String listCommentsForTab3(String selectedTopic, String selectedCluster, String selectedCompany, String selectedRadioButton, String searchedAfterDate) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append(" SELECT (GROUP_CONCAT(DISTINCT ?ArticleT) as ?ArticleTitle) ")
                .append("         (GROUP_CONCAT(DISTINCT ?NewsCategories) as ?New) ")
                .append("         (GROUP_CONCAT(DISTINCT ?otherTopic; SEPARATOR=\", \") as ?Topics) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Clust) as ?Cluster) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Comp) as ?Company) ")
                .append("         (GROUP_CONCAT(DISTINCT ?CommT; SEPARATOR=\"<n>\") as ?Comments) ")
                .append("         (GROUP_CONCAT(DISTINCT ?Date; SEPARATOR=\"<n>\") as ?CommentDate)\n")
                .append("     WHERE { ?NC	rdfs:subClassOf*	?parent .\n")
                .append("  		?NC	news:NewsCategory_Name	?NewsCategories .\n")
                .append("  		?Article a news:Article ;\n")
                .append("                 news:has_comment ?Comm ;\n")
                .append("                 news:Article_Title ?ArticleT ;\n")
                .append("                 news:is_in_news_category ?NC ;\n")
                .append("                 news:is_about_topic ?Topicc ;\n")
                .append("                   news:is_about_topic ?otTopic ;\n")
                .append("                 news:has_reporter ?Rep .\n")
                .append("  		?Rep news:works_at ?comp .\n")
                .append("  		?comp news:NewsCompany_Name ?Comp .\n")
                .append("               ?Topicc news:Topic_Name ?Topic .\n")
                .append("               ?otTopic news:Topic_Name ?otherTopic . \n")
                .append("  		?Comm news:Comment_Text ?CommT ;\n")
                .append("                   news:Comment_Date ?Date .")
                .append("               OPTIONAL {?Article news:has_cluster ?Cl .}")
                .append("  		OPTIONAL {?Cl news:Cluster_Name ?Clust . FILTER( !bound( ?Cl ) ) }\n");

        queryStr.append(filterStringForComments(selectedTopic, selectedCluster, selectedCompany, searchedAfterDate))
                .append(orderByStringForTab3(selectedRadioButton));

        return queryStr.toString();

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
     * @return
     */
    protected static String listArticlesForTab4(String selectedCategory, String selectedTopic, String selectedCompany, String selectedCluster, String selectedRadioButton, String searchedAfterDate) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append("SELECT (GROUP_CONCAT(DISTINCT ?Reps; SEPARATOR=\"; \") as ?Reporters) ")
                .append("       (GROUP_CONCAT(DISTINCT ?NewsCategories; SEPARATOR=\", \") as ?New) ")
                .append("       (GROUP_CONCAT(DISTINCT ?Comp) as ?Company)\n")
                .append("       (GROUP_CONCAT(DISTINCT ?ArticleT; SEPARATOR=\"<n>\") as ?ArticleTitle) ")
                .append("       (GROUP_CONCAT(DISTINCT ?otherTopic; SEPARATOR=\", \") as ?Topics) ")
                .append("       (GROUP_CONCAT(DISTINCT ?Clust; SEPARATOR=\", \") as ?Cluster) ")
                .append("       (GROUP_CONCAT(DISTINCT ?Date; SEPARATOR=\"<n>\") as ?ArticleDate)\n")
                .append("	WHERE { ?NC	rdfs:subClassOf*	?parent .\n")
                .append("  		?NC	news:NewsCategory_Name	?NewsCategories .\n")
                .append("  		?Article a news:Article ;\n")
                .append("                   news:Article_Title ?ArticleT ;\n")
                .append("                   news:is_in_news_category ?NC ;\n")
                .append("                   news:is_about_topic ?Topicc ;\n")
                .append("                   news:is_about_topic ?otTopic ;\n")
                .append("                   news:Article_Date ?Date ;\n")
                .append("                   news:has_reporter ?Rep .\n")
                .append("  		?Rep news:works_at ?comp ;\n")
                .append("                   news:is_expert_in ?NC ;\n")
                .append("                   news:Reporter_Name ?Reps .\n")
                .append("  		?comp news:NewsCompany_Name ?Comp .\n")
                .append("               ?Topicc news:Topic_Name ?Topic .\n")
                .append("               ?otTopic news:Topic_Name ?otherTopic . \n")
                .append("  		OPTIONAL {?Article news:has_cluster ?Cl .}\n")
                .append("		OPTIONAL {?Cl news:Cluster_Name ?Clust . FILTER( !bound( ?Cl ) ) }\n");

        queryStr.append(filterStringForArticles(selectedCategory, selectedTopic, selectedCompany, selectedCluster, searchedAfterDate))
                .append(orderByStringForArticlesForTab4(selectedRadioButton));

        return queryStr.toString();
    }

    /**
     * List article titles for tab5 - only article titles
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
    protected static String listArticleTitlesForTab5(String searchedWord, boolean isHeadlineSelected, boolean isArticleTextSelected, boolean isCommentsSelected, boolean isArticleTitleSelected) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append(" SELECT (GROUP_CONCAT(DISTINCT ?ArticleT; SEPARATOR=\"; \") as ?ArticleTitle) ")
                .append("	WHERE { ?Article a news:Article ;\n")
                .append("                   news:Article_Title ?ArticleT ;\n")
                .append("                   news:Article_Text ?ArticleText ; \n")
                .append("                   news:Article_Date ?Date ;\n")
                .append("                   news:has_a_headline ?headline .\n")
                .append("               ?headline news:Headline_Text ?HeadlineT .\n")
                .append("               OPTIONAL {?Article news:has_comment ?Comm .}\n")
                .append("               OPTIONAL {?Comm news:Comment_Text ?Comments . FILTER( !bound( ?Comm ) ) }\n");

        queryStr.append(filterStringForRelatedArticleTitles(searchedWord, isHeadlineSelected, isArticleTextSelected, isCommentsSelected, isArticleTitleSelected))
                .append("       }\n")
                .append("GROUP BY ?ArticleT\n	ORDER BY ?ArticleT");

        return queryStr.toString();

    }

    /**
     * List article titles for tab5 - related article titles
     *
     * @param selectedArticle
     * @return
     */
    protected static String listRelatedArticles(String selectedArticle) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append(PREFIX)
                .append(" SELECT (GROUP_CONCAT(DISTINCT ?RelArticle; SEPARATOR=\"; \") as ?RelatedArticleTitle) ")
                .append("	WHERE { ?Article a news:Article ;\n")
                .append("                   news:Article_Title ?ArticleT ;\n")
                .append("                   news:Article_Text ?ArticleText ; \n")
                .append("                   news:Article_Date ?Date ;\n")
                .append("                   news:has_a_headline ?headline .\n")
                .append("               ?headline news:Headline_Text ?HeadlineT .\n")
                .append("               ?Article news:has_related_articles ?relArticle .\n")
                .append("               ?relArticle news:Article_Title ?RelArticle . \n")
                .append("               OPTIONAL {?Article news:has_comment ?Comm .}\n")
                .append("               OPTIONAL {?Comm news:Comment_Text ?Comments . FILTER( !bound( ?Comm ) ) }\n")
                .append("       FILTER(regex(str(?ArticleT), \"").append(selectedArticle).append("\", \"i\"))\n")
                .append("       }\n")
                .append("GROUP BY ?RelArticle\n	ORDER BY ?RelatedArticleTitle");

        return queryStr.toString();

    }

    /**
     * List Topics for jComboBox
     *
     * @return
     */
    protected static String listTopics() {

        String queryStr = PREFIX
                + "SELECT (?Topic as ?Topics) "
                + "	WHERE { ?Topicc news:Topic_Name ?Topic . "
                + "	}"
                + "ORDER BY ASC(?Topic)";

        return queryStr;
    }

    /**
     * List Clusters for jComboBox
     *
     * @return
     */
    protected static String listClusters() {

        String queryStr = PREFIX
                + "SELECT (?Cluster as ?Clusters) "
                + "	WHERE { ?Clus news:Cluster_Name ?Cluster . "
                + "	}"
                + "ORDER BY ASC(?Cluster)";

        return queryStr;
    }

    /**
     * List Companies for jComboBox
     *
     * @return
     */
    protected static String listCompanies() {

        String queryStr = PREFIX
                + "SELECT (?Comp as ?Company) "
                + "	WHERE { ?Comps news:NewsCompany_Name ?Comp . "
                + "	}\n"
                + "ORDER BY ASC(?Comp)";

        return queryStr;
    }

    /**
     * List Categories for jComboBox
     *
     * @return
     */
    protected static String listCategories() {

        String queryStr = PREFIX
                + "SELECT DISTINCT (?NewsCategories as ?Categories)\n"
                + "	WHERE { ?NC	rdfs:subClassOf*	?parent .\n"
                + "  		?NC	news:NewsCategory_Name	?NewsCategories\n"
                + "	}\n"
                + "ORDER BY ASC(?NewsCategories)";

        return queryStr;

    }

    /**
     * Generating filter strings for tab3
     *
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedCompany - value of the jComboBox for Company
     * @param searchedAfterDate - value of the jTextField for the Date filter
     * @return
     */
    private static String filterStringForComments(String selectedTopic, String selectedCluster, String selectedCompany, String searchedAfterDate) {
        StringBuilder filterStr = new StringBuilder();

        if (!selectedTopic.equals("Topic")) {
            filterStr.append("FILTER(regex(str(?Topic), \"").append(selectedTopic).append("\"))\n");
        }

        if (!selectedCluster.equals("Cluster")) {
            filterStr.append("FILTER(regex(str(?Clust), \"").append(selectedCluster).append("\"))\n");
        }

        if (!selectedCompany.equals("Company")) {
            filterStr.append("FILTER(regex(str(?Comp), \"").append(selectedCompany).append("\"))\n");
        }

        if (!searchedAfterDate.equals("")) {
            filterStr.append("FILTER( ?Date > \"").append(searchedAfterDate).append("T00:00:00\"^^xsd:dateTime )\n");
        }

        return filterStr.toString();
    }

    /**
     * Generating filter strings for tab4
     *
     * @param selectedCategory - value of the jComboBox for Category
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     * @return
     */
    private static String filterStringForArticles(String selectedCategory, String selectedTopic, String selectedCompany, String selectedCluster, String searchedAfterDate) {
        StringBuilder filterStr = new StringBuilder();

        if (!selectedCategory.equals("Category")) {
            filterStr.append("FILTER(regex(str(?NewsCategories), \"").append(selectedCategory).append("\"))\n");
        }

        if (!selectedTopic.equals("Topic")) {
            filterStr.append("FILTER(regex(str(?Topic), \"").append(selectedTopic).append("\"))\n");
        }

        if (!selectedCompany.equals("Company")) {
            filterStr.append("FILTER(regex(str(?Comp), \"").append(selectedCompany).append("\"))\n");
        }

        if (!selectedCluster.equals("Cluster")) {
            filterStr.append("FILTER(regex(str(?Clust), \"").append(selectedCluster).append("\"))\n");
        }

        if (!searchedAfterDate.equals("")) {
            filterStr.append("FILTER( ?Date > \"").append(searchedAfterDate).append("T00:00:00\"^^xsd:dateTime )\n");
        }
        System.out.print("asd " + filterStr);
        return filterStr.toString();
    }

    /**
     * Generating filter strings for tab5
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
    private static String filterStringForRelatedArticleTitles(String searchedWord, boolean isHeadlineSelected, boolean isArticleTextSelected, boolean isCommentSelected, boolean isArticleTitleSelected) {

        StringBuilder filterStr = new StringBuilder();

        if ("".equals(searchedWord)) {
            return "";
        }

        if (isHeadlineSelected) {
            filterStr.append("FILTER(regex(str(?HeadlineT), \"").append(searchedWord).append("\", \"i\"))\n");
        }

        if (isArticleTextSelected) {
            filterStr.append("FILTER(regex(str(?ArticleText), \"").append(searchedWord).append("\", \"i\"))\n");
        }

        if (isCommentSelected) {
            filterStr.append("FILTER(regex(str(?Comments), \"").append(searchedWord).append("\", \"i\"))\n");
        }

        if (isArticleTitleSelected) {
            filterStr.append("FILTER(regex(str(?ArticleT), \"").append(searchedWord).append("\", \"i\"))\n");
        }

        return filterStr.toString();

    }

    /**
     * Generate ORDER BY String for tab4
     *
     * @param selectedRadioButton
     * @return
     */
    private static String orderByStringForArticlesForTab4(String selectedRadioButton) {
        String orderBy = "}\n	GROUP BY ?Reps ";

        if (selectedRadioButton.equals("Date")) {
            orderBy = "	}\n	GROUP BY ?Reps ?Date \n	ORDER BY ASC(?Date)	";
        }

        return orderBy;
    }

    /**
     * Generate ORDER BY String for tab3
     * @param selectedRadioButton
     * @return 
     */
    private static String orderByStringForTab3(String selectedRadioButton) {

        String orderBy = "";

        switch (selectedRadioButton) {
            case "":
                orderBy = "}\n	GROUP BY ?ArticleT\n";
                break;
            case "Topic":
                orderBy = "}\n	GROUP BY ?ArticleT ?Topic\n"
                        + "ORDER BY ASC(?Topic)";
                break;
            case "Cluster":
                orderBy = "}\n	GROUP BY ?ArticleT ?Clust\n"
                        + "ORDER BY ASC(?Clust)";
                break;
            case "Company":
                orderBy = "}\n	GROUP BY ?ArticleT ?Comp\n"
                        + "ORDER BY ASC(?Comp)";
                break;
            case "News Category":
                orderBy = "}\n	GROUP BY ?ArticleT ?NewsCategories\n"
                        + "ORDER BY ASC(?NewsCategories)";
                break;
            case "Date":
                orderBy = "}\n	GROUP BY ?ArticleT ?Date\n"
                        + "ORDER BY ASC(?Date)";
                break;
            default:
                break;
        }

        return orderBy;
    }

}
