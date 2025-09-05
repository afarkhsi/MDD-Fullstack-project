import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { AuthGuard } from './core/guards/auth.guards';
import { GuestGuard } from './core/guards/guest.guards';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { AccountComponent } from './pages/account/account.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent, title: 'Monde de Dév', canActivate: [GuestGuard] },
  { path: 'register', component: RegisterComponent, title: 'Register', canActivate: [GuestGuard] },
  { path: 'login', component: LoginComponent, title: 'Login', canActivate: [GuestGuard] },
  { path: 'account', component: AccountComponent, title: 'Account', canActivate: [AuthGuard] },
  { path: 'articles/create', component: CreateArticleComponent, title: 'Créer un article', canActivate: [AuthGuard] },
  { path: 'articles', component: ArticlesComponent, title: 'Articles', canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent, title: 'Article Détail', canActivate: [AuthGuard]  },
  { path: 'topics', component: TopicsComponent, title: 'Topics', canActivate: [AuthGuard],  },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
],
  exports: [RouterModule]
})
export class AppRoutingModule { }