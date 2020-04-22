import java.lang.reflect.Proxy;

public class JdkProxy{
    public static void main(String[] args){
        /**
        * ClassLoader 加载对象字节码，和被代理对象使用相同的类加载器。（固定写法）
        * Class[] 被代理对象的所有接口，让代理对象和被代理对象有相同的方法。（固定写法）
        * InvocationHandler 用于提供增强的代码，即如何代理。一般写该接口的实现类，通常情况是匿名内部类。
        */
        // IProducer经销商接口
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(),
        new InvocationHandler() {
            /**
             * 所有动态代理类的方法调用，都会交由invoke()方法去处理（都会经过该方法）
             * @param proxy 代理对象的引用（一般不用）
             * @param method 当前执行的方法
             * @param args 当前执行方法所需的参数
             * @return 返回代理后的对象
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object returnValue = null;
                // 提供增强的方法
                // 1.获取方法执行的参数
                Float money = (Float)args[0];
                // 2.判断当前方法是不是销售
                if (method.getName().equals("saleProduct")){
                    returnValue = method.invoke(producer, money*0.8f);
                }
                return returnValue;
            }
        });
        //结果就是，在不修改saleProduct方法的前提下，完成了对该方法的增强
        proxyProducer.saleProduct(10000f);
    }
}