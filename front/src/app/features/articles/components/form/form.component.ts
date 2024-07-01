import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ArticleResponse } from '../../interfaces/api/articleResponse.interface';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public articleForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
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
    const formData = new FormData();
    formData.append('name', this.articleForm!.get('name')?.value);
    formData.append('surface', this.articleForm!.get('surface')?.value);
    formData.append('price', this.articleForm!.get('price')?.value);
    if (!this.onUpdate) {
      formData.append('picture', this.articleForm!.get('picture')?.value._files[0]);
    }
    formData.append('description', this.articleForm!.get('description')?.value);

    if (!this.onUpdate) {
      this.articlesService
        .create(formData)
        .subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    } else {
      this.articlesService
        .update(this.id!, formData)
        .subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    }
  }

  private initForm(article?: Article): void {
    console.log(article);
    console.log(this.sessionService.user!.id);
    if( (article !== undefined) && (article?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/articles']);
    }
    this.articleForm = this.fb.group({
      tilte: [article ? article.title : '', [Validators.required]],
      author: [article ? article.author : '', [Validators.required]],
      content: [article ? article.content : '', [Validators.required]],
      theme: [article ? article.theme : '', [Validators.required]],
    });
    if (!this.onUpdate) {
      this.articleForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['articles']);
  }
}