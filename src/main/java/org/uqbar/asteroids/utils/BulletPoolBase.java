package org.uqbar.asteroids.utils;

import nf.fr.eraasoft.pool.PoolException;
import nf.fr.eraasoft.pool.PoolableObjectBase;

import org.uqbar.asteroids.components.Bullet;

public class BulletPoolBase extends PoolableObjectBase<Bullet> 
{

	@Override
	public void activate(Bullet bullet) throws PoolException {
		if(bullet==null){
			System.out.println("La bala obtenida era nula");
			bullet = new Bullet();
		}
		bullet.reset();
	}

	@Override
	public Bullet make() throws PoolException {
		return new Bullet();
	}
	
	
	
//	@Override
//	public Bullet create() throws Exception {
//		return new Bullet();
//	}
//
//	@Override
//	public PooledObject<Bullet> wrap(Bullet bullet) {
//		return new DefaultPooledObject<Bullet>(bullet);
//	}
//	

}
