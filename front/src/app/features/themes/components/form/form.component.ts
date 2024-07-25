import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ThemeResponse } from '../../interfaces/api/themeResponse.interface';
import { Theme } from '../../interfaces/theme.interface';
import { ThemesService } from '../../services/themes.service';
import { ThemeService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent implements OnInit {
  public onUpdate: boolean = false;
  public themeForm: FormGroup | undefined;
  public themes$ = this.themeService.all();

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private themesService: ThemesService,
    private themeService: ThemeService,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
    }
  }

  public submit(): void {
    const theme = this.themeForm?.value as FormData;
    /* this.themesService
    .create(theme)
    .subscribe((_: Theme) => this.exitPage('Theme created !'));*/
    const formData = new FormData();
    formData.append('title', this.themeForm!.get('title')?.value);
    formData.append('content', this.themeForm!.get('content')?.value);
    formData.append('theme_id', this.themeForm!.get('theme_id')?.value);
    if (!this.onUpdate) {
      this.themesService
        .create(theme)
        .subscribe((themeResponse: ThemeResponse) =>
          this.exitPage(themeResponse)
        );
    }
  }


  private exitPage(themeResponse: ThemeResponse): void {
    this.matSnackBar.open(themeResponse.message, 'Close', { duration: 3000 });
    this.router.navigate(['/themes']);
  }
}