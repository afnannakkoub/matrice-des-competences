import { inject } from '@angular/core';
import {
  CanActivateFn,
  Router
} from '@angular/router';

import { AuthService } from '../services/auth.service';


export const authGuard: CanActivateFn = (
  route,
  state
) => {

  const authService =
    inject(AuthService);

  const router =
    inject(Router);


  // ==========================================
  // NOT LOGGED IN
  // ==========================================

  if (!authService.isLoggedIn()) {

    return router.createUrlTree([
      '/login'
    ]);

  }


  // ==========================================
  // CHECK ROLE
  // ==========================================

  const expectedRoles =
    route.data?.['roles'] as string[] | undefined;


  if (
    expectedRoles &&
    expectedRoles.length > 0
  ) {

    const userRole =
      authService.getRole();


    if (
      !userRole ||
      !expectedRoles.includes(userRole)
    ) {

      return router.createUrlTree([
        '/unauthorized'
      ]);

    }
  }


  return true;
};