import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, title: 'Monde de Dév' },
  { path: 'register', component: RegisterComponent, title: 'Register' },
  { path: 'login', component: LoginComponent, title: 'Login' },
  { path: 'articles', component: ArticlesComponent, title: 'Articles' },
  { path: 'topics', component: TopicsComponent, title: 'Topics' },
  { path: 'articles/create', component: CreateArticleComponent, title: 'Créer un article' },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
],
  exports: [RouterModule]
})
export class AppRoutingModule { }