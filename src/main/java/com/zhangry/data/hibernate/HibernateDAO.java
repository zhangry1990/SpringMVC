package com.zhangry.data.hibernate;

import com.zhangry.common.exception.DAOException;
import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.common.page.QueryParameter.Sort;
import com.zhangry.common.util.AssertUtil;
import com.zhangry.common.util.StringUtil;
import com.zhangry.ssh.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/20.
 */
public class HibernateDAO<T, ID extends Serializable> extends SimpleHibernateDAO<T, ID> implements BaseDao<T, ID> {
    public HibernateDAO() {
    }

    public Page<T> findPage(QueryParameter queryParameter, String hql, Object... values) {
        return this.findPageInterval(queryParameter, hql, false, (Object[])values);
    }

    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Object... values) {
        return this.findPageInterval(queryParameter, hql, true, (Object[])values);
    }

    private Page<T> findPageInterval(QueryParameter queryParameter, String hql, boolean isCacheable, Object... values) {
        AssertUtil.notNull(queryParameter, "queryParameter不能为空");
        int pageNo = queryParameter.getPageNo();
        int pageSize = queryParameter.getPageSize();
        Page page = new Page(pageSize, true);
        if(pageNo <= 0) {
            pageNo = 1;
        }

        page.setPageNo(pageNo);
        page.setSortList(queryParameter.getSortList());
        if(page.isAutoCount()) {
            long q = this.countHqlResult(hql, (Object[])values);
            page.setTotalRows((int)q);
            page.setTotalCount((int)q);
        }

        if(page.getSortList().size() > 0) {
            hql = this.setOrderParameterToHql(hql, page);
        }

        Query q1 = this.createQuery(hql, values);
        if(isCacheable) {
            q1.setCacheable(true);
        }

        this.setPageParameterToQuery(q1, page);
        List result = q1.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(QueryParameter queryParameter, String hql, Map<String, ?> values) {
        return this.findPageInterval(queryParameter, hql, false, (Map)values);
    }

    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Map<String, ?> values) {
        return this.findPageInterval(queryParameter, hql, true, (Map)values);
    }

    private Page<T> findPageInterval(QueryParameter queryParameter, String hql, boolean isCacheable, Map<String, ?> values) {
        AssertUtil.notNull(queryParameter, "queryParameter不能为空");
        int pageNo = queryParameter.getPageNo();
        int pageSize = queryParameter.getPageSize();
        Page page = new Page(pageSize, true);
        if(pageNo <= 0) {
            pageNo = 1;
        }

        page.setPageNo(pageNo);
        if(page.isAutoCount()) {
            long q = this.countHqlResult(hql, (Map)values);
            page.setTotalRows((int)q);
            page.setTotalCount((int)q);
        }

        if(page.getSortList().size() > 0) {
            hql = this.setOrderParameterToHql(hql, page);
        }

        Query q1 = this.createQuery(hql, values);
        this.setPageParameterToQuery(q1, page);
        List result;
        if(isCacheable) {
            result = q1.setCacheable(true).list();
        } else {
            result = q1.list();
        }

        page.setResult(result);
        return page;
    }

    public List<Map<String, Object>> findBySql(String sql, Object... values) {
        try {
            SQLQuery e = this.createSqlQuery(sql, (Object[])values);
            List list = e.setResultTransformer(new ResultToMap()).list();
            return list;
        } catch (Exception var5) {
            var5.printStackTrace();
            throw new DAOException(var5);
        }
    }

    public List<Map<String, Object>> findBySql(String sql, Map<String, Object> values) {
        try {
            SQLQuery e = this.createSqlQuery(sql, (Map)values);
            return e.setResultTransformer(new ResultToMap()).list();
        } catch (Exception var4) {
            throw new DAOException(var4);
        }
    }

    public Page<T> findPageBySql(QueryParameter queryParameter, String sql, Object... values) {
        return this.findBySqlInternel(queryParameter, sql, values);
    }

    public Page<T> findPageBySql(QueryParameter queryParameter, String sql, Map<String, ?> values) {
        return this.findBySqlInternel(queryParameter, sql, values);
    }

    private Page<T> findBySqlInternel(QueryParameter queryParameter, String sql, Object obj) {
        AssertUtil.notNull(obj);
        boolean isMap = false;
        if(obj instanceof Map) {
            isMap = true;
        }

        try {
            Page e = new Page();
            e.setPageNo(queryParameter.getPageNo());
            e.setPageSize(queryParameter.getPageSize());
            e.setAutoCount(queryParameter.isAutoCount());
            e.setSortList(queryParameter.getSortList());
            if(e.isAutoCount()) {
                try {
                    String q = this.prepareCountHql(sql);
                    SQLQuery cq;
                    if(isMap) {
                        cq = this.createSqlQuery(q, (Map)((Map)obj));
                    } else {
                        cq = this.createSqlQuery(q, (Object[])((Object[])((Object[])obj)));
                    }

                    Object o = cq.uniqueResult();
                    Long count = Long.valueOf(0L);
                    if(o instanceof Long) {
                        count = (Long)o;
                    } else if(o instanceof BigDecimal) {
                        BigDecimal b = (BigDecimal)o;
                        count = Long.valueOf(b.longValue());
                    } else if(o instanceof BigInteger) {
                        BigInteger b1 = (BigInteger)o;
                        count = Long.valueOf(b1.longValue());
                    }

                    e.setTotalCount(count.intValue());
                } catch (Exception var11) {
                    throw new DAOException(var11);
                }
            }

            if(e.getPageNo() > e.getTotalPages()) {
                e.setPageNo(1);
            }

            SQLQuery q1;
            if(isMap) {
                q1 = this.createSqlQuery(sql, (Map)((Map)obj));
            } else {
                q1 = this.createSqlQuery(sql, (Object[])((Object[])((Object[])obj)));
            }

            q1.addEntity(this.entityClass);
            if(e.isFirstSetted()) {
                q1.setFirstResult(e.getFirst());
            }

            if(e.isPageSizeSetted()) {
                q1.setMaxResults(e.getPageSize());
            }

            e.setResult(q1.list());
            return e;
        } catch (HibernateException var12) {
            throw new DAOException(var12);
        }
    }

    public <T> List<T> findBySql(Class<T> clazz, String sql, Object... values) {
        try {
            return this.createSqlQuery(sql, (Object[])values).addEntity(clazz).list();
        } catch (Exception var5) {
            throw new DAOException(var5);
        }
    }

    public <T> List<T> findBySql(Class<T> clazz, String sql, Map<String, ?> values) {
        try {
            return this.createSqlQuery(sql, (Map)values).addEntity(clazz).list();
        } catch (Exception var5) {
            throw new DAOException(var5);
        }
    }

    public int executeSql(String sql, Object... values) {
        return this.createSqlQuery(sql, (Object[])values).executeUpdate();
    }

    public int executeSql(String sql, Map<String, ?> values) {
        return this.createSqlQuery(sql, (Map)values).executeUpdate();
    }

    public int executeBatchSql(String sql, List<Map<String, Object>> dataList, String... columns) {
        AssertUtil.notEmpty(dataList);
        AssertUtil.notEmpty(columns);
        PreparedStatement pstmt = null;
        Map map = null;

        try {
            pstmt = SessionFactoryUtils.getDataSource(this.sessionFactory).getConnection().prepareStatement(sql);
            Iterator e = dataList.iterator();

            while(e.hasNext()) {
                Map aDataList = (Map)e.next();
                map = aDataList;
                int e1 = 1;
                String[] var9 = columns;
                int var10 = columns.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    String key = var9[var11];
                    pstmt.setObject(e1, map.get(key));
                    ++e1;
                }

                pstmt.addBatch();
            }

            int[] var39 = pstmt.executeBatch();
            this.logger.info("====================当前sql = [" + sql + "] execute counts : " + var39.length + " ===================");
            int var40 = var39.length;
            return var40;
        } catch (Exception var37) {
            this.logger.error("sql 执行失败， 当前sql = [" + sql + "], map = [" + map + "]", var37);
            throw new RuntimeException(var37);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException var35) {
                this.logger.error(var35.getMessage(), var35);
                throw new RuntimeException(var35);
            } finally {
                if(pstmt != null) {
                    pstmt = null;
                }

            }

        }
    }

    public int executeBatchSql(String sql, List<List<String>> dataList) {
        PreparedStatement pstmt = null;
        List data = null;

        try {
            pstmt = SessionFactoryUtils.getDataSource(this.sessionFactory).getConnection().prepareStatement(sql);

            int j;
            for(int e = 0; e < dataList.size(); ++e) {
                data = (List)dataList.get(e);
                j = 1;

                for(Iterator e1 = data.iterator(); e1.hasNext(); ++j) {
                    String value = (String)e1.next();
                    pstmt.setObject(j, value);
                }

                pstmt.addBatch();
            }

            int[] var35 = pstmt.executeBatch();
            j = var35.length;
            return j;
        } catch (Exception var33) {
            this.logger.error("sql 执行失败， 当前sql = [" + sql + "], data = [" + data + "]", var33);
            throw new DAOException(var33);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException var31) {
                this.logger.error(var31.getMessage(), var31);
                throw new DAOException(var31);
            } finally {
                if(pstmt != null) {
                    pstmt = null;
                }

            }

        }
    }

    public int executeBatchSql(List<String> sqlList) {
        Statement stmt = null;
        String currentSql = null;

        try {
            stmt = SessionFactoryUtils.getDataSource(this.sessionFactory).getConnection().createStatement();

            for(int e = 0; e < sqlList.size(); ++e) {
                currentSql = (String)sqlList.get(e);
                stmt.addBatch(currentSql);
            }

            int[] var33 = stmt.executeBatch();
            int var5 = var33.length;
            return var5;
        } catch (Exception var31) {
            this.logger.error("sql 执行失败， 当前sql = [" + currentSql + "]", var31);
            throw new RuntimeException(var31);
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var29) {
                this.logger.error(var29.getMessage(), var29);
                throw new RuntimeException(var29);
            } finally {
                if(stmt != null) {
                    stmt = null;
                }

            }

        }
    }

    private String setOrderParameterToHql(String hql, Page page) {
        StringBuilder builder = new StringBuilder(hql);
        builder.append(" ORDER BY");
        Iterator var4 = page.getSortList().iterator();

        while(var4.hasNext()) {
            Sort orderBy = (Sort)var4.next();
            builder.append(orderBy.toString());
        }

        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private Query setPageParameterToQuery(Query q, Page page) {
        if(page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }

        q.setMaxResults(page.getPageSize());
        return q;
    }

    private Criteria setPageRequestToCriteria(Criteria c, Page page) {
        AssertUtil.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
        c.setFirstResult(page.getFirst());
        c.setMaxResults(page.getPageSize());
        if(page.isOrderBySetted()) {
            Iterator var3 = page.getSortList().iterator();

            while(var3.hasNext()) {
                Sort sort = (Sort)var3.next();
                if(sort.isAsc()) {
                    c.addOrder(Order.asc(sort.getFieldName()));
                } else {
                    c.addOrder(Order.desc(sort.getFieldName()));
                }
            }
        }

        return c;
    }

    private long countHqlResult(String hql, Object... values) {
        String countHql = null;

        try {
            countHql = this.prepareCountHql(hql);
            return ((Long)this.createQuery(countHql, values).uniqueResult()).longValue();
        } catch (Exception var5) {
            throw new DAOException("hql can\'t be auto count, hql is:" + countHql);
        }
    }

    private long countHqlResult(String hql, Map<String, ?> values) {
        String countHql = null;

        try {
            countHql = this.prepareCountHql(hql);
            return ((Long)this.createQuery(countHql, values).uniqueResult()).longValue();
        } catch (Exception var5) {
            throw new DAOException("hql can\'t be auto count, hql is:" + countHql);
        }
    }

    private String prepareCountHql(String orgHql) {
        return "select count (*) " + StringUtil.removeSelect(StringUtil.removeOrders(orgHql));
    }

    protected SQLQuery createSqlQuery(String sql, Object... values) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        if(values != null) {
            for(int i = 0; i < values.length; ++i) {
                if(values[i] != null) {
                    sqlQuery.setParameter(i, values[i]);
                }
            }
        }

        return sqlQuery;
    }

    protected SQLQuery createSqlQuery(String sql, Map<String, ?> values) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        if(values != null) {
            sqlQuery.setProperties(values);
        }

        return sqlQuery;
    }
}

