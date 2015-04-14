# Guice Foundation
----

## Guice的作用
- IoC（依赖注入）
- AoP（面向切面）
- Factory Pattern
- "com.google.inject:guice:latest.release"


## Binding和Inject：

### Module

Module用于对Injector进行配置

> 假设有这样一个接口：
	
	public interface BindDemo {
	    String test();
	}

> 这样来实现它：

	public class BindDemoImpl implements BindDemo {
        @Override
        public String test() {
            return "Hello World";
        }
    }

> 绑定接口和实现类

	public class ModuleDemo implements Module {
	    @Override
	    public void configure(Binder binder) {
	        binder.bind(BindDemo.class).to(BindDemoImpl.class);
	    }
	}

> 装载`Module`并获取对象实例

	Injector injector = Guice.createInjector(new ModuleDemo());    
	BindDemo instance = injector.getInstance(BindDemo.class);
	assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));

> 问题：如何为一个接口绑定不同的实现类？


### 命名绑定

> 现在用如下两个类实现`BindDemo`类：

    public class BindDemoImplA implements BindDemo {
        @Override
        public String test() {
            return "Demo A";
        }
    }

>

    public class BindDemoImplB implements BindDemo {
        @Override
        public String test() {
            return "Demo B";
        }
    }

> 定义如下注解：

	@Documented
	@BindingAnnotation // This Annotation is used in guice binding
	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
	}

>

	@Documented
	@BindingAnnotation
	@Retention(RetentionPolicy.RUNTIME)
	public @interface B {
	}

> 利用注解进行绑定

	public class ModuleDemo extends AbstractModule {
	    @Override
	    protected void configure() {
	        bind(BindDemo.class).annotatedWith(A.class).to(BindDemoImplA.class);
	        bind(BindDemo.class).annotatedWith(B.class).to(BindDemoImplB.class);
	    }
	}

> 利用注解获取对象实例：

	Injector injector = Guice.createInjector(new ModuleDemo());

	BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, A.class));
    assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplA.class));

	bindDemo = injector.getInstance(Key.get(BindDemo.class, B.class));
    assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplB.class));

> 利用别名进行绑定：

	public class AliensBindingModule extends AbstractModule {
	    @Override
	    protected void configure() {
	        bind(BindDemo.class).annotatedWith(Names.named("A")).to(BindDemoImplA.class);
	        bind(BindDemo.class).annotatedWith(Names.named("B")).to(BindDemoImplB.class);
	    }
	}

>

	Injector injector = Guice.createInjector(new ModuleDemo());

	BindDemo bindDemo = injector.getInstance(Key.get(BindDemo.class, Names.named("A")));
	assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplA.class));

	bindDemo = injector.getInstance(Key.get(BindDemo.class, Names.named("B")));
	assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImplB.class));

### 指定实例化所用的构造器

> 如果一个类具有多个构造器，那应该用哪一个构造器来实例化对象呢？

> 假设有如下接口实现类：

	public static class BindDemoImpl implements BindDemo {
        private String value;

		public BindDemoImpl() {
			value = "Blank";
		}

        public BindDemoImpl(String value) {
            this.value = value;
        }

        @Override
        public String test() {
            return value;
        }
    }

> 绑定时指定所用的构造器：

	public class ModuleDemo implements Module {
	    @Override
	    public void configure(Binder binder) {
	        try {
	            Constructor<BindDemoImpl> constructor = BindDemoImpl.class.getConstructor(String.class);
	            binder.bind(BindDemo.class).toConstructor(constructor);
	            binder.bind(String.class).toInstance("Hello World");
	        } catch (NoSuchMethodException e) {
	            binder.addError(e);
	        }
	    }
	}

>

	Injector injector = Guice.createInjector(new ModuleDemo());	
		
    BindDemo bindDemo = injector.getInstance(BindDemo.class);
    assertThat(bindDemo.getClass(), typeCompatibleWith(BindDemoImpl.class));
	assertThat(bindDemo.test()	, is("Hello World"));

### 绑定泛型接口

> 对于泛型接口，在绑定实现类时，需要指定泛型类型：

	public class ModuleDemo extends AbstractModule {
	    public static final String[] VALUES = {"A", "B", "C"};

	    @Override
	    protected void configure() {
	        bind(new TypeLiteral<Set<String>>() {})
                .to(new TypeLiteral<HashSet<String>>() {});
	
	        bind(new TypeLiteral<Set<Integer>>() {})
                .to(new TypeLiteral<TreeSet<Integer>>() {});
	    }
	}

>

	Injector injector = Guice.createInjector(new ModuleDemo());	

	Set<String> set1 = injector.getInstance(Key.get(new TypeLiteral<Set<String>>() {}));
	assertThat(set1.getClass(), typeCompatibleWith(HashSet.class));

	Set<Integer> set2 = injector.getInstance(Key.get(new TypeLiteral<Set<Integer>>() {}));
	assertThat(set2.getClass(), typeCompatibleWith(TreeSet.class));

### 绑定Provider

> Provider可以以工厂模式来产生对象：

> 有如下Provider类：

	public class CacheProvider implements Provider<BindDemo> {
        private static final ThreadLocal<BindDemo> LOCAL = new ThreadLocal<>();

        @Override
        public BindDemo get() {
            BindDemo bindDemo = LOCAL.get();
            if (bindDemo == null) {
                bindDemo = new BindDemoImpl();
                LOCAL.set(bindDemo);
            }
            return bindDemo;
        }
    }

> 绑定接口和该Provider类：

	public class ModuleDemo extends AbstractModule {
	
	    @Override
	    protected void configure() {
	        bind(BindDemo.class).toProvider(CacheProvider.class);
	    }
	}

> 得到Provider类实例，用其来产生对象：

	Injector injector = Guice.createInjector(new ModuleDemo());	
	
	Provider<BindDemo> provider = injector.getProvider(BindDemo.class);
    BindDemo bindDemo = provider.get();
	assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));

### 单例模式

> 对于无状态的类，使用单例模式可以提高系统效率，节省内存使用：

	public class ModuleDemo extends AbstractModule {
        @Override
        public void configure(Binder binder) {
            binder.bind(BindDemo.class).to(BindDemoImpl.class).in(Scopes.SINGLETON);
        }
	}


## 使用注解：

> 大部分场合下，并无需使用`Injector`对象，注解是更为简便明确的方式：

### @Inject

`@Inject`用于在一个类中使用，可以有三种用法：

> 在字段上注解

	public class InjectByField {
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }	

> 在构造器上注解

	public class InjectByConstructor {
        private String value;

        @Inject
        public InjectByConstructor(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

> 在`setXXX`方法上注解

	public class InjectByMethod {
        private String value;

        public String getValue() {
            return value;
        }

        @Inject
        public void setValue(String value) {
            this.value = value;
        }
    }

> 测试一下：

	public class Test {
		private static final String VALUE = "TEST";
	
	    @Inject
	    private InjectByField injectByField;
	
	    @Inject
	    private InjectByConstructor injectByConstructor;
	
	    @Inject
	    private InjectByMethod injectByMethod;

	    @Before
	    public void setup() {
	        Guice.createInjector(new AbstractModule() {
    	        @Override
    	        protected void configure() {
    	            bind(String.class).toProvider(() -> VALUE);
    	        }
    	    }).injectMembers(this);
	    }

		@Test
		public void test() {
	        assertThat(injectByField.getValue(), is(VALUE));
			assertThat(injectByConstructor.getValue(), is(VALUE));
			assertThat(injectByMethod.getValue(), is(VALUE));
	    }
	}

### @Provides

`@Provides`注解可以在不写Provider类的前提下提供一个Provider实例：

> 书写一个带@Provides注解的Module：

	public class ModuleDemo extends AbstractModule {
	    private static final ThreadLocal<BindDemo> LOCAL = new ThreadLocal<>();
	
	    @Override
	    protected void configure() {
	    }
	
	    @Provides
	    public BindDemo instanceProvider() {
	        BindDemo bindDemo = LOCAL.get();
            if (bindDemo == null) {
                bindDemo = new BindDemoImpl();
                LOCAL.set(bindDemo);
            }
            return bindDemo;
	    }
	}

>

	public class Test {
	    @Inject
	    private Provider<BindDemo> provider;
	
	    @Before
	    public void setup() {
	        Guice.createInjector(new ModuleDemo()).injectMembers(this);
	    }

		@Test
		public void test() {
    		BindDemo bindDemo = provider.get();
			assertThat(instance.getClass(), typeCompatibleWith(BindDemoImpl.class));
	    }
	}

> 配合`@Inject`注解，可以为Provider方法注入参数：

	@Provides
	@Inject
    public BindDemo instanceProvider(Argument1 arg1, Argument2 arg2) {
        ....
        return bindDemo;
    }

### 自定义注解和@Named

这两种注解都是为了区分相同类型的不同对象（也可以是相同接口的不同实现类）

> 定义如下注解：

	@Documented
	@BindingAnnotation // This Annotation is used in guice binding
	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
	}

>

	@Documented
	@BindingAnnotation
	@Retention(RetentionPolicy.RUNTIME)
	public @interface B {
	}

> 绑定

	public class ModuleDemo extends AbstractModule {
    	public static final String VALUEA = "A", VALUEB = "B";

	    @Override
	    protected void configure() {
	        bind(String.class).annotatedWith(A.class).toInstance(VALUEA);
	    }
		
		@B
	    @Provides
	    public String valueAProvider() {
	        return VALUEB;
	    }
	}

> 使用

	public class InjectA {
        @A
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }

>

    public class InjectB {
        private String value;

        @Inject
        public InjectB(@B String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


也可以使用`@Named`注解，避免过多的自定义注解

	public class ModuleDemo extends AbstractModule {
    	public static final String VALUEA = "A", VALUEB = "B";

	    @Override
	    protected void configure() {
	        bind(String.class).annotatedWith(Names.named("A")).toInstance(VALUEA);
	    }
		
		@Named("B")
	    @Provides
	    public String valueAProvider() {
	        return VALUEB;
	    }
	}

>

	public class InjectA {
        @Named("A")
        @Inject
        private String value;

        public String getValue() {
            return value;
        }
    }

>

    public class InjectB {
        private String value;

        @Inject
        public InjectB(@Named("B") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


### @Singleton

`@Singleton`注解表示被注解的类只能生成单例实例，对于无状态的类应该使用`@Singleton`注解

	@Singleton
	public class SingletonClass {
		public void method() {
			...
		}
	}

> `@Singleton`和`@Provides`注解结合，则得到一个单例的Provider

    public class ModuleDemo extends AbstractModule {
	    private static final ThreadLocal<BindDemo> LOCAL = new ThreadLocal<>();
	
	    @Override
	    protected void configure() {
	    }
	
	    @Provides
		@Singleton
	    public BindDemo instanceProvider() {
	        BindDemo bindDemo = LOCAL.get();
            if (bindDemo == null) {
                bindDemo = new BindDemoImpl();
                LOCAL.set(bindDemo);
            }
            return bindDemo;
	    }
	}

> 显然，上例中使用`@Singleton`是不合适的，令`ThreadLocal`类失去了作用

## 装载子Module

Guice支持在一个Module中装载其它Module，从而使`Injector`的使用更加灵活，以便在不同场景下得到不同的配置

	public class InstallerModule extends AbstractModule {	
	    public static final String MODULEA = "A";
	    public static final String MODULEB = "B";
	
	    // The value to means which module would be install
	    private String moduleName;
	
	    public InstallerModule(String moduleName) {
	        this.moduleName = moduleName;
	    }

	    @Override
	    protected void configure() {
	        switch (moduleName) {
	        case MODULEA:
	            install(new AbstractModule() {
	                @Override
	                protected void configure() {
	                    bind(BindDemo.class).to(BindDemoImplA.class);
	                }
	
	                @Named("VALUE")
	                @Provides
	                public String valueProvider() {
	                    return MODULEA;
	                }
	            });
	            break;
	        case MODULEB:
	            install(new AbstractModule() {
	                @Override
	                protected void configure() {
	                    bind(BindDemo.class).to(BindDemoImplB.class);
	                }
	
	                @Named("VALUE")
	                @Provides
	                public String valueProvider() {
	                    return MODULEB;
	                }
	            });
	            break;
	        default:
	            throw new IllegalArgumentException("moduleName");
	        }
	    }
	
	    @Provides
	    @Named("NAME")
	    public String moduleNameProvider() {
	        return moduleName;
	    }
	}