import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isAuthenticated = false;
  showHeader = true;
  isMobile = window.innerWidth <= 800;
  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService, 
    private router: Router,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit(): void {
    this.authService.authStatus$
    .pipe(takeUntil(this.destroy$))
    .subscribe(status => {
      this.isAuthenticated = status;
    });
    // Détecte responsive en direct
    this.breakpointObserver
      .observe([`(max-width: 800px)`])
      .pipe(takeUntil(this.destroy$))
      .subscribe(result => {
        this.isMobile = result.matches;
        this.updateHeaderVisibility(this.router.url);
      });

    // Détecte la route active
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe(event => {
        this.updateHeaderVisibility(event.urlAfterRedirects);
      });
  }

  private updateHeaderVisibility(currentRoute: string) {
    // Routes où le header est toujours masqué
    if (currentRoute === '/home') {
      this.showHeader = false;
      return;
    }

    // Routes où le header est masqué uniquement en mobile
    if (['/login', '/register'].includes(currentRoute) && this.isMobile) {
      this.showHeader = false;
      return;
    }

    this.showHeader = true;
  }
  
  logout(): void {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
