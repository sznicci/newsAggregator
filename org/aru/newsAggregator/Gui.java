/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aru.newsAggregator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * User Interface
 *
 * @author Nikoletta Szedljak
 */
public class Gui extends javax.swing.JFrame {

    DefaultListModel model;

    /**
     * Creates new form Gui
     */
    public Gui() {

        this.getContentPane();
        initComponents();

    }

    /**
     * Load all articles for tab2 Presents the selected article in the
     * jEditorPane_ShowArticleForAllArticleTitles panel
     */
    private void loadAllArticles() {

        DefaultListModel<String> listModel = new DefaultListModel();

        String[] data = QueryBuilding.createArticleTitleList();
        for (String str : data) {
            listModel.addElement(str);
        }

        jList_ForAllArticleTitles.setModel(listModel);
        jList_ForAllArticleTitles.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {

                if (evt.getValueIsAdjusting()) {
                    return;
                }
                String selectedArticle = jList_ForAllArticleTitles.getSelectedValue();
                selectedArticle = selectedArticle.replace("?", "\\\\?");
                selectedArticle = selectedArticle.replace("(", "\\\\(");
                selectedArticle = selectedArticle.replace(")", "\\\\)");

                jEditorPane_ShowArticleForAllArticleTitles.setText(htmlSnippet()[0] + QueryBuilding.createPresentSelectedArticle(selectedArticle) + htmlSnippet()[1]);
                jEditorPane_ShowArticleForAllArticleTitles.setCaretPosition(0);
            }
        });

    }

    /**
     * Load Comments for tab3
     *
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     */
    private void loadComments(String selectedTopic, String selectedCluster, String selectedCompany, String selectedRadioButton, String searchedAfterDate) {

        jEditorPane_Comments.setText(htmlSnippet()[0] + QueryBuilding.createCommentsList(selectedTopic, selectedCluster, selectedCompany, selectedRadioButton, searchedAfterDate) + htmlSnippet()[1]);
        jEditorPane_Comments.setCaretPosition(0);

    }

    /**
     * Load articles for tab4
     *
     * @param selectedCategory - value of the jComboBox for Category
     * @param selectedTopic - value of the jComboBox for Topic
     * @param selectedCompany - value of the jComboBox for Company
     * @param selectedCluster - value of the jComboBox for Cluster
     * @param selectedRadioButton - value of the jRadioButton for the Order By
     * String
     * @param searchedAfterDate - value of the jTextField for the Date filter
     */
    private void loadArticles(String selectedCategory, String selectedTopic, String selectedCompany, String selectedCluster, String selectedRadioButton, String searchedAfterDate) {

        jEditorPane_ForArticles.setText(htmlSnippet()[0] + QueryBuilding.createArticlesList(selectedCategory, selectedTopic, selectedCompany, selectedCluster, selectedRadioButton, searchedAfterDate) + htmlSnippet()[1]);
        jEditorPane_ForArticles.setCaretPosition(0);

    }

    /**
     * Load article titles for tab5 Present related article titles in
     * jEditorPane_ShowArticleForRelatedArticleTitles
     *
     * @param searchedWord - value of the searched word from
     * jTextField_ForRelatedArticles
     * @param isHeadlineSelected - value of jCheckBox_HeadlineForRelatedArticles
     * @param isArticleTextSelected - value of
     * jCheckBox_ArticleTextForRelatedArticles
     * @param isCommentsSelected - value of jCheckBox_CommentsForRelatedArticles
     * @param isArticleTitleSelected - value of
     * jCheckBox_ArticleTitleForRelatedArticles
     */
    private void loadArticleTitles(String searchedWord, boolean isHeadlineSelected, boolean isArticleTextSelected, boolean isCommentsSelected, boolean isArticleTitleSelected) {

        DefaultListModel<String> listModel = new DefaultListModel();

        String[] data = QueryBuilding.createArtTitles(searchedWord, isHeadlineSelected, isArticleTextSelected, isCommentsSelected, isArticleTitleSelected);
        for (String str : data) {
            listModel.addElement(str);
        }
        jList_ForRelatedArticles.setModel(listModel);
        jList_ForRelatedArticles.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {

                if (evt.getValueIsAdjusting()) {
                    return;
                }
                String selectedArticle = jList_ForRelatedArticles.getSelectedValue();
                selectedArticle = selectedArticle.replace("?", "\\\\?");
                selectedArticle = selectedArticle.replace("(", "\\\\(");
                selectedArticle = selectedArticle.replace(")", "\\\\)");

                jEditorPane_ShowArticleForRelatedArticleTitles.setText(htmlSnippet()[0] + QueryBuilding.createRelatedeArticleTitles(selectedArticle) + htmlSnippet()[1]);
                jEditorPane_ShowArticleForRelatedArticleTitles.setCaretPosition(0);
            }
        });
    }

    /**
     * HTML snippet for formatting
     *
     * @return String array with HTML header and footer
     */
    private static String[] htmlSnippet() {
        String[] strArray = new String[2];

        String htmlHeader = "<html>\n"
                + "   <head>\n"
                + "     <title>An example HTMLDocument</title>\n"
                + "     <style type=\"text/css\">\n"
                + "         p { border-style: solid; \n border-color: #92a8d1; }\n"
                + "         h1 { color : #0E4672; \n background-color : white; }\n"
                + "         h2 { color : #65ADE6; \n background-color : white; }\n"
                + "         h3 { color : #565757; \n background-color : white; }\n"
                + "         div { padding : 25px 45px 25px 45px; \n margin : 0px 20px 5px 20px; }\n"
                + "         .secondDiv { padding : 15px 15px 15px 15px; \n margin : 10px 10px 10px 10px; }\n"
                + "         hr { color: #565757; }\n"
                + "     </style>\n"
                + "   </head>\n"
                + "   <body>\n";
        String htmlFooter = "</body>\n"
                + " </html>";

        strArray[0] = htmlHeader;
        strArray[1] = htmlFooter;

        return strArray;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jSeparator2 = new javax.swing.JSeparator();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane_ForArticles = new javax.swing.JTabbedPane();
        jPanel_Home = new javax.swing.JPanel();
        jLabel_Home = new javax.swing.JLabel();
        jPanel_ForAllArticleTitles = new javax.swing.JPanel();
        jButton_SubmitForArticles = new javax.swing.JButton();
        jScrollPane_ForAllArticleTitles = new javax.swing.JScrollPane();
        jList_ForAllArticleTitles = new javax.swing.JList<>();
        jScrollPane_ShowArticleForAllArticleTitles = new javax.swing.JScrollPane();
        jEditorPane_ShowArticleForAllArticleTitles = new javax.swing.JEditorPane();
        jPanel_ForComments = new javax.swing.JPanel();
        jScrollPane_Comments = new javax.swing.JScrollPane();
        jEditorPane_Comments = new javax.swing.JEditorPane();
        jComboBox_TopicForComments = new javax.swing.JComboBox<>();
        jLabel_TopicForComments = new javax.swing.JLabel();
        jComboBox_ClusterForComments = new javax.swing.JComboBox<>();
        jComboBox_CompanyForComments = new javax.swing.JComboBox<>();
        jTextField_DateForComments = new javax.swing.JTextField();
        jLabel_ClusterForComments = new javax.swing.JLabel();
        jLabel_CompanyForComments = new javax.swing.JLabel();
        jLabel_DateForComments = new javax.swing.JLabel();
        jButton_SubmitForComments = new javax.swing.JButton();
        jRadioButton_DateForComments = new javax.swing.JRadioButton();
        jRadioButton_CompanyForComments = new javax.swing.JRadioButton();
        jRadioButton_ClusterForComments = new javax.swing.JRadioButton();
        jRadioButton_TopicForComments = new javax.swing.JRadioButton();
        jLabel_DateFormatForComments = new javax.swing.JLabel();
        jPanel_ForArticlesWrittenBy = new javax.swing.JPanel();
        jScrollPane_Articles = new javax.swing.JScrollPane();
        jEditorPane_ForArticles = new javax.swing.JEditorPane();
        jComboBox_CategoryForArticles = new javax.swing.JComboBox<>();
        jComboBox_TopicForArticles = new javax.swing.JComboBox<>();
        jComboBox_CompanyForArticles = new javax.swing.JComboBox<>();
        jComboBox_ClusterForArticles = new javax.swing.JComboBox<>();
        jTextField_DateForArticles = new javax.swing.JTextField();
        jButton_SubmitForArticlesWrittenByExperts = new javax.swing.JButton();
        jLabel_CategoryForArticles = new javax.swing.JLabel();
        jLabel_TopicForArticles = new javax.swing.JLabel();
        jLabel_CompanyForArticles = new javax.swing.JLabel();
        jLabel_ClusterForArticles = new javax.swing.JLabel();
        jLabel_DateForArticles = new javax.swing.JLabel();
        jRadioButton_DateForArticles = new javax.swing.JRadioButton();
        jLabel_OrderForArticles = new javax.swing.JLabel();
        jLabel_DateFormatForArticles = new javax.swing.JLabel();
        jLabel_Category2 = new javax.swing.JLabel();
        jPanel_ForRelatedArticles = new javax.swing.JPanel();
        jLabel_SearchForRelatedArticles = new javax.swing.JLabel();
        jTextField_ForRelatedArticles = new javax.swing.JTextField();
        jCheckBox_HeadlineForRelatedArticles = new javax.swing.JCheckBox();
        jCheckBox_ArticleTextForRelatedArticles = new javax.swing.JCheckBox();
        jCheckBox_CommentsForRelatedArticles = new javax.swing.JCheckBox();
        jLabel_ShowForRelatedArticles = new javax.swing.JLabel();
        jButton_ShowArticlesForRelatedArticles = new javax.swing.JButton();
        jScrollPane_ForListForRelatedArticles = new javax.swing.JScrollPane();
        jList_ForRelatedArticles = new javax.swing.JList(new DefaultListModel());
        jScrollPane_ShowArticleForRelatedArticleTitles1 = new javax.swing.JScrollPane();
        jEditorPane_ShowArticleForRelatedArticleTitles = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox_ArticleTitleForRelatedArticles = new javax.swing.JCheckBox();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(37, 70, 135));

        jTabbedPane_ForArticles.setBackground(new java.awt.Color(37, 70, 135));

        jLabel_Home.setIcon(new javax.swing.ImageIcon("G:\\jena\\src-examples\\jena-examples\\backgroundImage\\newsOwn01.png")); // NOI18N

        javax.swing.GroupLayout jPanel_HomeLayout = new javax.swing.GroupLayout(jPanel_Home);
        jPanel_Home.setLayout(jPanel_HomeLayout);
        jPanel_HomeLayout.setHorizontalGroup(
            jPanel_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_HomeLayout.createSequentialGroup()
                .addComponent(jLabel_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel_HomeLayout.setVerticalGroup(
            jPanel_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_HomeLayout.createSequentialGroup()
                .addComponent(jLabel_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 716, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane_ForArticles.addTab("Home", jPanel_Home);

        jPanel_ForAllArticleTitles.setBackground(new java.awt.Color(37, 70, 135));

        jButton_SubmitForArticles.setText("List All Article Titles");
        jButton_SubmitForArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SubmitForArticlesActionPerformed(evt);
            }
        });

        jList_ForAllArticleTitles.setBackground(new java.awt.Color(189, 220, 242));
        jScrollPane_ForAllArticleTitles.setViewportView(jList_ForAllArticleTitles);

        jScrollPane_ShowArticleForAllArticleTitles.setBackground(new java.awt.Color(206, 237, 255));

        jEditorPane_ShowArticleForAllArticleTitles.setEditable(false);
        jEditorPane_ShowArticleForAllArticleTitles.setContentType("text/html");
        jEditorPane_ShowArticleForAllArticleTitles.setSelectionColor(new java.awt.Color(209, 236, 236));
        jScrollPane_ShowArticleForAllArticleTitles.setViewportView(jEditorPane_ShowArticleForAllArticleTitles);

        javax.swing.GroupLayout jPanel_ForAllArticleTitlesLayout = new javax.swing.GroupLayout(jPanel_ForAllArticleTitles);
        jPanel_ForAllArticleTitles.setLayout(jPanel_ForAllArticleTitlesLayout);
        jPanel_ForAllArticleTitlesLayout.setHorizontalGroup(
            jPanel_ForAllArticleTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ForAllArticleTitlesLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel_ForAllArticleTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForAllArticleTitlesLayout.createSequentialGroup()
                        .addComponent(jButton_SubmitForArticles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_ForAllArticleTitlesLayout.createSequentialGroup()
                        .addComponent(jScrollPane_ForAllArticleTitles, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane_ShowArticleForAllArticleTitles, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_ForAllArticleTitlesLayout.setVerticalGroup(
            jPanel_ForAllArticleTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ForAllArticleTitlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_SubmitForArticles)
                .addGap(18, 18, 18)
                .addGroup(jPanel_ForAllArticleTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_ShowArticleForAllArticleTitles, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                    .addComponent(jScrollPane_ForAllArticleTitles))
                .addContainerGap())
        );

        jTabbedPane_ForArticles.addTab("Articles", jPanel_ForAllArticleTitles);

        jPanel_ForComments.setBackground(new java.awt.Color(37, 70, 135));

        jScrollPane_Comments.setBackground(new java.awt.Color(206, 237, 255));

        jEditorPane_Comments.setEditable(false);
        jEditorPane_Comments.setContentType("text/html");
        jScrollPane_Comments.setViewportView(jEditorPane_Comments);

        jComboBox_TopicForComments.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createTopicsList().toArray()));

        jLabel_TopicForComments.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_TopicForComments.setText("Choose a Topic");

        jComboBox_ClusterForComments.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createClustersList().toArray()));

        jComboBox_CompanyForComments.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createCompaniesList().toArray()));

        jLabel_ClusterForComments.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_ClusterForComments.setText("Choose a Cluster");

        jLabel_CompanyForComments.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_CompanyForComments.setText("Choose a News Company");

        jLabel_DateForComments.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_DateForComments.setText("List Comments After Date");

        jButton_SubmitForComments.setText("Submit");
        jButton_SubmitForComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SubmitForCommentsActionPerformed(evt);
            }
        });

        jRadioButton_DateForComments.setBackground(new java.awt.Color(208, 237, 237));
        buttonGroup1.add(jRadioButton_DateForComments);
        jRadioButton_DateForComments.setForeground(new java.awt.Color(206, 237, 255));
        jRadioButton_DateForComments.setText("Date");

        jRadioButton_CompanyForComments.setBackground(new java.awt.Color(208, 237, 237));
        buttonGroup1.add(jRadioButton_CompanyForComments);
        jRadioButton_CompanyForComments.setForeground(new java.awt.Color(206, 237, 255));
        jRadioButton_CompanyForComments.setText("News Company");

        jRadioButton_ClusterForComments.setBackground(new java.awt.Color(208, 237, 237));
        buttonGroup1.add(jRadioButton_ClusterForComments);
        jRadioButton_ClusterForComments.setForeground(new java.awt.Color(206, 237, 255));
        jRadioButton_ClusterForComments.setText("Cluster");

        jRadioButton_TopicForComments.setBackground(new java.awt.Color(208, 237, 237));
        buttonGroup1.add(jRadioButton_TopicForComments);
        jRadioButton_TopicForComments.setForeground(new java.awt.Color(206, 237, 255));
        jRadioButton_TopicForComments.setText("Topic");

        jLabel_DateFormatForComments.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_DateFormatForComments.setText("Date format: \"dd-mm-yyyy\"");

        javax.swing.GroupLayout jPanel_ForCommentsLayout = new javax.swing.GroupLayout(jPanel_ForComments);
        jPanel_ForComments.setLayout(jPanel_ForCommentsLayout);
        jPanel_ForCommentsLayout.setHorizontalGroup(
            jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ForCommentsLayout.createSequentialGroup()
                .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                                .addComponent(jLabel_TopicForComments)
                                .addGap(85, 85, 85)
                                .addComponent(jLabel_ClusterForComments)
                                .addGap(82, 82, 82)
                                .addComponent(jLabel_CompanyForComments))
                            .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                                .addComponent(jComboBox_TopicForComments, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_ClusterForComments, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(jComboBox_CompanyForComments, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                                .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_DateForComments)
                                    .addComponent(jTextField_DateForComments, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addComponent(jButton_SubmitForComments))
                            .addComponent(jLabel_DateFormatForComments)))
                    .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jRadioButton_TopicForComments)
                        .addGap(105, 105, 105)
                        .addComponent(jRadioButton_ClusterForComments)
                        .addGap(113, 113, 113)
                        .addComponent(jRadioButton_CompanyForComments)
                        .addGap(81, 81, 81)
                        .addComponent(jRadioButton_DateForComments))
                    .addGroup(jPanel_ForCommentsLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jScrollPane_Comments, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel_ForCommentsLayout.setVerticalGroup(
            jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ForCommentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_DateForComments, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_TopicForComments)
                    .addComponent(jLabel_ClusterForComments)
                    .addComponent(jLabel_CompanyForComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_DateFormatForComments))
                .addGap(4, 4, 4)
                .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_TopicForComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_ClusterForComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_CompanyForComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_DateForComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_SubmitForComments))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ForCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_TopicForComments)
                    .addComponent(jRadioButton_ClusterForComments)
                    .addComponent(jRadioButton_CompanyForComments)
                    .addComponent(jRadioButton_DateForComments))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane_Comments, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTabbedPane_ForArticles.addTab("Comments", jPanel_ForComments);

        jPanel_ForArticlesWrittenBy.setBackground(new java.awt.Color(37, 70, 135));

        jScrollPane_Articles.setBackground(new java.awt.Color(206, 237, 255));

        jEditorPane_ForArticles.setEditable(false);
        jEditorPane_ForArticles.setContentType("text/html");
        jScrollPane_Articles.setViewportView(jEditorPane_ForArticles);

        jComboBox_CategoryForArticles.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createCategoryList().toArray()));

        jComboBox_TopicForArticles.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createTopicsList().toArray()));

        jComboBox_CompanyForArticles.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createCompaniesList().toArray()));

        jComboBox_ClusterForArticles.setModel(new javax.swing.DefaultComboBoxModel(QueryBuilding.createClustersList().toArray()));

        jButton_SubmitForArticlesWrittenByExperts.setText("Submit");
        jButton_SubmitForArticlesWrittenByExperts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SubmitForArticlesWrittenByExpertsActionPerformed(evt);
            }
        });

        jLabel_CategoryForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_CategoryForArticles.setLabelFor(jComboBox_CategoryForArticles);
        jLabel_CategoryForArticles.setText("Choose a Category");
        jLabel_CategoryForArticles.setMaximumSize(new java.awt.Dimension(100, 16));
        jLabel_CategoryForArticles.setMinimumSize(new java.awt.Dimension(100, 16));
        jLabel_CategoryForArticles.setPreferredSize(new java.awt.Dimension(100, 16));

        jLabel_TopicForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_TopicForArticles.setText("Choose a Topic");

        jLabel_CompanyForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_CompanyForArticles.setText("Choose a Company");

        jLabel_ClusterForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_ClusterForArticles.setText("Choose a Cluster");

        jLabel_DateForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_DateForArticles.setText("List Articles after Date");

        jRadioButton_DateForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jRadioButton_DateForArticles.setText("by Date");

        jLabel_OrderForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_OrderForArticles.setText("Order");

        jLabel_DateFormatForArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_DateFormatForArticles.setText("Date format: \"dd-mm-yyyy\"");

        jLabel_Category2.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_Category2.setText("Written by an expert in that Category");

        javax.swing.GroupLayout jPanel_ForArticlesWrittenByLayout = new javax.swing.GroupLayout(jPanel_ForArticlesWrittenBy);
        jPanel_ForArticlesWrittenBy.setLayout(jPanel_ForArticlesWrittenByLayout);
        jPanel_ForArticlesWrittenByLayout.setHorizontalGroup(
            jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jScrollPane_Articles, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox_CategoryForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Category2))
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox_TopicForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel_TopicForArticles)))
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(jLabel_OrderForArticles)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton_DateForArticles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                                .addComponent(jLabel_CompanyForArticles)
                                .addGap(30, 30, 30))
                            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                                .addComponent(jComboBox_CompanyForArticles, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                                .addComponent(jLabel_ClusterForArticles)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_DateFormatForArticles))
                            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                                .addComponent(jComboBox_ClusterForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_DateForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23)
                .addComponent(jButton_SubmitForArticlesWrittenByExperts)
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel_CategoryForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_DateForArticles)
                .addGap(248, 248, 248))
        );
        jPanel_ForArticlesWrittenByLayout.setVerticalGroup(
            jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_CategoryForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_DateForArticles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_TopicForArticles, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_ClusterForArticles)
                        .addComponent(jLabel_DateFormatForArticles)
                        .addComponent(jLabel_CompanyForArticles)
                        .addComponent(jLabel_Category2)))
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_ClusterForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_SubmitForArticlesWrittenByExperts)
                                .addComponent(jTextField_DateForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel_ForArticlesWrittenByLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_CompanyForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_TopicForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_CategoryForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_ForArticlesWrittenByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_DateForArticles)
                    .addComponent(jLabel_OrderForArticles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_Articles, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTabbedPane_ForArticles.addTab("Articles Written By Experts", jPanel_ForArticlesWrittenBy);

        jPanel_ForRelatedArticles.setBackground(new java.awt.Color(37, 70, 135));

        jLabel_SearchForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_SearchForRelatedArticles.setText("Search for text");

        jCheckBox_HeadlineForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jCheckBox_HeadlineForRelatedArticles.setText("Headline");

        jCheckBox_ArticleTextForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jCheckBox_ArticleTextForRelatedArticles.setText("Article text");

        jCheckBox_CommentsForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jCheckBox_CommentsForRelatedArticles.setText("Comments");

        jLabel_ShowForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jLabel_ShowForRelatedArticles.setText("In");

        jButton_ShowArticlesForRelatedArticles.setText("Show Articles");
        jButton_ShowArticlesForRelatedArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ShowArticlesForRelatedArticlesActionPerformed(evt);
            }
        });

        jList_ForRelatedArticles.setBackground(new java.awt.Color(189, 220, 242));
        jList_ForRelatedArticles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane_ForListForRelatedArticles.setViewportView(jList_ForRelatedArticles);

        jScrollPane_ShowArticleForRelatedArticleTitles1.setBackground(new java.awt.Color(206, 237, 255));

        jEditorPane_ShowArticleForRelatedArticleTitles.setEditable(false);
        jEditorPane_ShowArticleForRelatedArticleTitles.setContentType("text/html");
        jScrollPane_ShowArticleForRelatedArticleTitles1.setViewportView(jEditorPane_ShowArticleForRelatedArticleTitles);

        jLabel1.setForeground(new java.awt.Color(206, 237, 255));
        jLabel1.setText("Article Titles");

        jLabel2.setForeground(new java.awt.Color(206, 237, 255));
        jLabel2.setText("Related Article Titles");

        jCheckBox_ArticleTitleForRelatedArticles.setForeground(new java.awt.Color(206, 237, 255));
        jCheckBox_ArticleTitleForRelatedArticles.setText("Article Title");

        javax.swing.GroupLayout jPanel_ForRelatedArticlesLayout = new javax.swing.GroupLayout(jPanel_ForRelatedArticles);
        jPanel_ForRelatedArticles.setLayout(jPanel_ForRelatedArticlesLayout);
        jPanel_ForRelatedArticlesLayout.setHorizontalGroup(
            jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton_ShowArticlesForRelatedArticles))
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTextField_ForRelatedArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel_SearchForRelatedArticles))
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_CommentsForRelatedArticles)
                            .addComponent(jCheckBox_ArticleTextForRelatedArticles)
                            .addComponent(jCheckBox_HeadlineForRelatedArticles)
                            .addComponent(jCheckBox_ArticleTitleForRelatedArticles)))
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel_ShowForRelatedArticles)))
                .addGap(32, 32, 32)
                .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addComponent(jScrollPane_ForListForRelatedArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane_ShowArticleForRelatedArticleTitles1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(218, 218, 218))))
        );
        jPanel_ForRelatedArticlesLayout.setVerticalGroup(
            jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel_SearchForRelatedArticles)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_ForRelatedArticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel_ShowForRelatedArticles)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox_ArticleTitleForRelatedArticles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_HeadlineForRelatedArticles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_ArticleTextForRelatedArticles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_CommentsForRelatedArticles)
                        .addGap(32, 32, 32)
                        .addComponent(jButton_ShowArticlesForRelatedArticles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ForRelatedArticlesLayout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_ForRelatedArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane_ForListForRelatedArticles)
                            .addComponent(jScrollPane_ShowArticleForRelatedArticleTitles1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jTabbedPane_ForArticles.addTab("Related Articles", jPanel_ForRelatedArticles);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane_ForArticles, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane_ForArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Submit button for tab2
     *
     * @param evt
     */
    private void jButton_SubmitForArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SubmitForArticlesActionPerformed

        loadAllArticles();
    }//GEN-LAST:event_jButton_SubmitForArticlesActionPerformed

    /**
     * Submit button from tab3
     *
     * @param evt
     */
    private void jButton_SubmitForCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SubmitForCommentsActionPerformed

        String selectedTopic = (String) jComboBox_TopicForComments.getSelectedItem();
        String selectedCluster = (String) jComboBox_ClusterForComments.getSelectedItem();
        String selectedCompany = (String) jComboBox_CompanyForComments.getSelectedItem();
        String selectedRadioButton = "";
        String searchedAfterDate = "";

        if (jRadioButton_CompanyForComments.isSelected()) {
            selectedRadioButton = "Company";
        } else if (jRadioButton_ClusterForComments.isSelected()) {
            selectedRadioButton = "Cluster";
        } else if (jRadioButton_DateForComments.isSelected()) {
            selectedRadioButton = "Date";
        } else if (jRadioButton_TopicForComments.isSelected()) {
            selectedRadioButton = "Topic";
        }

        if (!jTextField_DateForComments.getText().equals("")) {
            searchedAfterDate = jTextField_DateForComments.getText();

            SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date date = df.parse(searchedAfterDate);
                searchedAfterDate = new SimpleDateFormat("yyyy-mm-dd").format(date);
            } catch (ParseException ex) {
                jEditorPane_Comments.setText("The date format is not correct! Pattern is: dd-mm-yyyy where yyyy = year, mm = month, dd = day.");
                return;
            }
        }

        loadComments(selectedTopic, selectedCluster, selectedCompany, selectedRadioButton, searchedAfterDate);
    }//GEN-LAST:event_jButton_SubmitForCommentsActionPerformed

    /**
     * Submit button for tab4
     *
     * @param evt
     */
    private void jButton_SubmitForArticlesWrittenByExpertsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SubmitForArticlesWrittenByExpertsActionPerformed

        String selectedTopic = (String) jComboBox_TopicForArticles.getSelectedItem();
        String selectedCluster = (String) jComboBox_ClusterForArticles.getSelectedItem();
        String selectedCompany = (String) jComboBox_CompanyForArticles.getSelectedItem();
        String selectedCategory = (String) jComboBox_CategoryForArticles.getSelectedItem();
        String searchedAfterDate = "";
        String selectedRadioButton = "";

        if (jRadioButton_DateForArticles.isSelected()) {
            selectedRadioButton = "Date";
        }

        if (!jTextField_DateForArticles.getText().equals("")) {
            searchedAfterDate = jTextField_DateForArticles.getText();

            SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date date = df.parse(searchedAfterDate);
                searchedAfterDate = new SimpleDateFormat("yyyy-mm-dd").format(date);
            } catch (ParseException ex) {
                jEditorPane_ForArticles.setText("The date format is not correct! Pattern is: dd-mm-yyyy where yyyy = year, mm = month, dd = day.");
                return;
            }
        }

        loadArticles(selectedCategory, selectedTopic, selectedCompany, selectedCluster, selectedRadioButton, searchedAfterDate);

    }//GEN-LAST:event_jButton_SubmitForArticlesWrittenByExpertsActionPerformed

    /**
     * Submit button for tab5
     *
     * @param evt
     */
    private void jButton_ShowArticlesForRelatedArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ShowArticlesForRelatedArticlesActionPerformed

        String searchedWord = jTextField_ForRelatedArticles.getText();
        boolean selectedHeadline = jCheckBox_HeadlineForRelatedArticles.isSelected();
        boolean selectedArticleText = jCheckBox_ArticleTextForRelatedArticles.isSelected();
        boolean selectedComments = jCheckBox_CommentsForRelatedArticles.isSelected();
        boolean isArticleTitleSelected = jCheckBox_ArticleTitleForRelatedArticles.isSelected();

        loadArticleTitles(searchedWord, selectedHeadline, selectedArticleText, selectedComments, isArticleTitleSelected);
    }//GEN-LAST:event_jButton_ShowArticlesForRelatedArticlesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton_ShowArticlesForRelatedArticles;
    private javax.swing.JButton jButton_SubmitForArticles;
    private javax.swing.JButton jButton_SubmitForArticlesWrittenByExperts;
    private javax.swing.JButton jButton_SubmitForComments;
    private javax.swing.JCheckBox jCheckBox_ArticleTextForRelatedArticles;
    private javax.swing.JCheckBox jCheckBox_ArticleTitleForRelatedArticles;
    private javax.swing.JCheckBox jCheckBox_CommentsForRelatedArticles;
    private javax.swing.JCheckBox jCheckBox_HeadlineForRelatedArticles;
    private javax.swing.JComboBox<String> jComboBox_CategoryForArticles;
    private javax.swing.JComboBox<String> jComboBox_ClusterForArticles;
    private javax.swing.JComboBox<String> jComboBox_ClusterForComments;
    private javax.swing.JComboBox<String> jComboBox_CompanyForArticles;
    private javax.swing.JComboBox<String> jComboBox_CompanyForComments;
    private javax.swing.JComboBox<String> jComboBox_TopicForArticles;
    private javax.swing.JComboBox<String> jComboBox_TopicForComments;
    private javax.swing.JEditorPane jEditorPane_Comments;
    private javax.swing.JEditorPane jEditorPane_ForArticles;
    private javax.swing.JEditorPane jEditorPane_ShowArticleForAllArticleTitles;
    private javax.swing.JEditorPane jEditorPane_ShowArticleForRelatedArticleTitles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Category2;
    private javax.swing.JLabel jLabel_CategoryForArticles;
    private javax.swing.JLabel jLabel_ClusterForArticles;
    private javax.swing.JLabel jLabel_ClusterForComments;
    private javax.swing.JLabel jLabel_CompanyForArticles;
    private javax.swing.JLabel jLabel_CompanyForComments;
    private javax.swing.JLabel jLabel_DateForArticles;
    private javax.swing.JLabel jLabel_DateForComments;
    private javax.swing.JLabel jLabel_DateFormatForArticles;
    private javax.swing.JLabel jLabel_DateFormatForComments;
    private javax.swing.JLabel jLabel_Home;
    private javax.swing.JLabel jLabel_OrderForArticles;
    private javax.swing.JLabel jLabel_SearchForRelatedArticles;
    private javax.swing.JLabel jLabel_ShowForRelatedArticles;
    private javax.swing.JLabel jLabel_TopicForArticles;
    private javax.swing.JLabel jLabel_TopicForComments;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList_ForAllArticleTitles;
    private javax.swing.JList<String> jList_ForRelatedArticles;
    private javax.swing.JPanel jPanel_ForAllArticleTitles;
    private javax.swing.JPanel jPanel_ForArticlesWrittenBy;
    private javax.swing.JPanel jPanel_ForComments;
    private javax.swing.JPanel jPanel_ForRelatedArticles;
    private javax.swing.JPanel jPanel_Home;
    private javax.swing.JRadioButton jRadioButton_ClusterForComments;
    private javax.swing.JRadioButton jRadioButton_CompanyForComments;
    private javax.swing.JRadioButton jRadioButton_DateForArticles;
    private javax.swing.JRadioButton jRadioButton_DateForComments;
    private javax.swing.JRadioButton jRadioButton_TopicForComments;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_Articles;
    private javax.swing.JScrollPane jScrollPane_Comments;
    private javax.swing.JScrollPane jScrollPane_ForAllArticleTitles;
    private javax.swing.JScrollPane jScrollPane_ForListForRelatedArticles;
    private javax.swing.JScrollPane jScrollPane_ShowArticleForAllArticleTitles;
    private javax.swing.JScrollPane jScrollPane_ShowArticleForRelatedArticleTitles1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane_ForArticles;
    private javax.swing.JTextField jTextField_DateForArticles;
    private javax.swing.JTextField jTextField_DateForComments;
    private javax.swing.JTextField jTextField_ForRelatedArticles;
    // End of variables declaration//GEN-END:variables
}
