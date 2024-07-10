import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ArticleResponse } from '../../interfaces/api/articleResponse.interface';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';
import { ThemeService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public articleForm: FormGroup | undefined;
  public themes$ = this.themeService.all();

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private articlesService: ArticlesService,
    private themeService: ThemeService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.articlesService
        .detail(this.id)
        .subscribe((article: Article) => this.initForm(article));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const article = this.articleForm?.value as FormData;
    /*this.articlesService
    .create(article)
    .subscribe((_: Article) => this.exitPage('Article created !'));*/
    /*const formData = new FormData();
    formData.append('title', this.articleForm!.get('title')?.value);
    formData.append('content', this.articleForm!.get('content')?.value);
    formData.append('theme_id', this.articleForm!.get('theme_id')?.value);*/
    if (!this.onUpdate) {
      this.articlesService
        .create(article)
        .subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    }
  }

  private initForm(article?: Article): void {
    /*console.log(article);
    console.log(this.sessionService.user!.id);
    if( (article !== undefined) && (article?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/articles']);
    }*/
    this.articleForm = this.fb.group({
      title: [article ? article.title : '', [Validators.required]],
      content: [article ? article.content : '', [Validators.required]],
      theme_id: [article ? article.theme_id : '', [Validators.required]],
    });
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['/articles']);
  }
}