/**
 * Java中七种单例模式的设计
 * 分别是饿汉式，懒汉式，懒汉式+同步方式，Double-Check，Volatile+Double-check，Holder，枚举 
 */
public class Singletons {
	
	/**
	 * 1.饿汉式
	 * 该模式可以保证多个线程下的唯一实例，但是无法进行懒加载，也即如果主动使用Singleton类，那么instance实例将会
	 * 直接完成创建，包括其中的实例变量都会被初始化，
	 */
	// final不允许被继承
	public final class Singleton1 {
		// 实例变量
		private byte[] data = new byte[1024];
		// 在定义实例对象的时候直接初始化
		private static Singleton instance = new Singleton1();
		
		// 私有构造函数，不允许外部new
		private Singleton1() {
			
		}
		
		public static Singleton1 getInstance() {
			return instance;
		}
	}
	
	/**
	 * 2.懒汉式
	 * 为了避免在初始化的时候提前创建实例,但是这种方式在多线程的情况下会出现问题（不是单例）
	 */
	public final class Singleton2 {
		// 实例变量
		private byte[] data = new byte[1024];
		// 定义实例，但是不直接初始化
		private static Singleton instance = null;
		
		private Singleton2() {
			
		}
		
		public static Singleton2 getInstance() {
			if (null == instance) {
				instance = new Singleton2();
			}
			return instance;
		}
	}
	
	/**
	 * 3.懒汉式+同步方法
	 * instance在单例模式中属于共享资源，因此，可以使用同步方法来进行对instance的访问
	 * 
	 * 使用synchronized的缺点就是，synchronized关键字天生的排他性导致了getInstance方法只能在同一时刻被一个线程
	 * 访问，性能低下。
	 */
	public final class Singleton3 {
		
		private byte[] data = new byte[1024];
		
		private static Singleton instance = null;
		
		private Singleton3() {
			
		}
		
		public static synchronized Singleton3 getInstance() {
			if (null == instance) {
				instance = new Singleton3();
			}
			return instance;
		}
	} 
	
	/**
	 * 4.Double-Check
	 * 该方式在多线程的方式下有可能会引起空指针异常。根据JVM运行时指令重排序和Happens-Before规则，如果在构造函数
	 * 中需要初始化其它实例变量的时候，有可能instance最先被实例化，而其它资源并未被实例化，所以会出现空指针异常。
	 */
	public final class Singleton4 {
		// 实例变量
		private byte[] data = new byte[1024];
		
		private static Singleton4 instance = null;
		
		Connection conn;
		
		Socket socket;
		
		private Singleton4() {
			// 初始化conn和socket
			this.conn;
			this.socket;
		}
		
		public static Singleton4 getInstance() {
			// 当instance为null时，进入同步代码块，同时该判断避免了每次都需要进入同步代码块，可以提高效率
			if (null == instance) {
				// 只有一个线程能够获得Singleton.class关联的monitor
				synchronized (Singleton4.class) {
					// 如果判断instance为null则创建
					if (null == instance) {
						instance = new Singleton4();
					}
				}
			}
			return instance;
		}
	}
	
	/**
	 * 5.Volatile+Double-Check
	 * 在模式4中，JVM的指令重排序可能会导致出现问题，因此可以使用Volatile关键字来对代码加载进行隔离，进而保证
	 * 不会出问题。
	 * 
	 * 该方法只是对模式4的代码进行微调即可。
	 */
	public final class Singleton5 {
		// 实例变量
		private byte[] data = new byte[1024];
		
		private static volatile Singleton5 instance = null;
		
		Connection conn;
		
		Socket socket;
		
		private Singleton5() {
			// 初始化conn和socket
			this.conn;
			this.socket;
		}
		
		public static Singleton5 getInstance() {
			// 当instance为null时，进入同步代码块，同时该判断避免了每次都需要进入同步代码块，可以提高效率
			if (null == instance) {
				// 只有一个线程能够获得Singleton.class关联的monitor
				synchronized (Singleton5.class) {
					// 如果判断instance为null则创建
					if (null == instance) {
						instance = new Singleton5();
					}
				}
			}
			return instance;
		}
	}
	
	/**
	 * 6.Holder方式
	 * Holder方式借助了类加载的特点，在Holder类中定义Singleton变量，并且直接进行初始化，当Holder被主动引用的时候
	 * 会创建Singleton实例，Singleton实例的创建过程在Java程序编译时期收集至<clinit>()方法中，该方法又是同步方法，
	 * 同步方法可以保证内存的可见性、JVM指令的顺序性和原子性
	 * 
	 * Holder方式是最好的设计之一
	 */
	public final class Singleton6 {
		// 实例变量
		private byte[] data = new byte[1024];
		
		private Singleton() {
			
		}
		
		// 在静态内部类中持有Singleton的实例，并且可被直接实例化
		private static class Holder {
			private static Singleton6 instance = new Singleton6();
		}
		
		// 调用getInstance方法，事实上是获得Holder的instance静态属性
		public static Singleton6 getInstance() {
			return Holder.instance;
		}
	}
	
	/**
	 * 7.枚举方式
	 * 枚举类型不允许被继承，同样是线程安全的且只能被实例化一次，但是枚举类型不能够懒加载，对Singleton主动使用，
	 * 比如调用其中的静态方法则INSTANCE会立即得到实例化
	 *
	 * 也可以对其进行改造，增加其懒加载的特性，类型与Holder的方式
	 */
	public enum Singleton7 {
		INSTANCE;
		// 实例变量
		private byte[] data = new byte[1024];
		
		Singleton7() {
			System.out.println("INSTANCE will be initialized immediately");
		}
		
		public static void method() {
			// 调用该方法则会主动使用Singleton7，INSTANCE将会被实例化
		}
		
		public static Singleton7 getInstance() {
			return INSTANCE;
		}
	}
	
	/**
	 * 枚举类型改造，增加懒加载
	 */
	public class Singleton {
		
		private byte[] data = new byte[1024];
		
		private Singleton() {
			
		}
		// 使用枚举充当Holder
		private enum EnumHolder {
			INSTANCE;
			private Singleton instance;
			
			EnumHolder() {
				this.instance = new Singleton();
			}
			
			private Singleton getSingleton() {
				return instance;
			}
		}
		
		public static Singleton getInstance() {
			return EnumHolder.INSTANCE.getSingleton();
		}
	}
}