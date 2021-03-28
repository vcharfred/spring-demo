package top.vchar.order.config;

/**
 * <p> 数据库配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/28
 */
//@Configuration
//@EnableConfigurationProperties({MybatisPlusProperties.class})
//public class DataSourceConfiguration {
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean
//    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
//        return new DataSourceProxy(dataSource);
//    }
//
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSourceProxy dataSourceProxy,
//                                                       MybatisPlusProperties mybatisProperties) {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSourceProxy);
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            Resource[] mapperLocations = resolver.getResources(mybatisProperties.getMapperLocations()[0]);
//            bean.setMapperLocations(mapperLocations);
//            if (StringUtils.isNotBlank(mybatisProperties.getConfigLocation())) {
//                Resource[] resources = resolver.getResources(mybatisProperties.getConfigLocation());
//                bean.setConfigLocation(resources[0]);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bean;
//    }
//}
