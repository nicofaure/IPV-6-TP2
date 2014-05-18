package org.uqbar.asteroids.utils;

import nf.fr.eraasoft.pool.ObjectPool;
import nf.fr.eraasoft.pool.PoolSettings;

import org.uqbar.asteroids.components.Bullet;

public class BulletPoolSingleton {

	private static volatile BulletPoolSingleton instance = null;
	private ObjectPool<Bullet> pool;
	
	private BulletPoolSingleton()
	{
		
		PoolSettings<Bullet> poolSettings = new PoolSettings<Bullet>(new BulletPoolBase());
		poolSettings.max(250);
		this.setPool(poolSettings.pool());

	}

    public static BulletPoolSingleton getInstance() {
        if (instance == null) {
            synchronized (BulletPoolSingleton.class) {
                if (instance == null) {
                    instance = new BulletPoolSingleton();
                }
            }
        }
        return instance;
    }
	
	public Bullet obtainBullet(){
		try
		{

			Bullet bullet = this.getPool().getObj();
			if(bullet!=null){
				System.out.println("Se obtuvo bala desde el pool: " + bullet.toString());
				return bullet;
			}else{
				System.out.println("La bala era nula, se tuvo que construir una nueva");
				return new Bullet();
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	

	public void returnBullet(Bullet bullet){
		try {
			System.out.println("Se devolvio una bala al pool:" + bullet.toString());
			this.getPool().returnObj(bullet);
			bullet.destroy();
		} catch (Exception e) {
			bullet.destroy();
			throw new RuntimeException(e);
		}
	}
	
	private ObjectPool<Bullet> getPool() {
		return pool;
	}

	private void setPool(ObjectPool<Bullet> pool) {
		this.pool = pool;
	}
	
	
}
