import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ThemeResponse } from '../../interfaces/api/themeResponse.interface';
import { Theme } from '../../interfaces/theme.interface';
import { ThemesService } from '../../services/themes.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public themeForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private themesService: ThemesService,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.themesService
        .detail(this.id)
        .subscribe((theme: Theme) => this.initForm(theme));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.themeForm!.get('name')?.value);
    formData.append('surface', this.themeForm!.get('surface')?.value);
    formData.append('price', this.themeForm!.get('price')?.value);
    if (!this.onUpdate) {
      formData.append('picture', this.themeForm!.get('picture')?.value._files[0]);
    }
    formData.append('description', this.themeForm!.get('description')?.value);

    if (!this.onUpdate) {
      this.themesService
        .create(formData)
        .subscribe((themeResponse: ThemeResponse) => this.exitPage(themeResponse));
    } else {
      this.themesService
        .update(this.id!, formData)
        .subscribe((themeResponse: ThemeResponse) => this.exitPage(themeResponse));
    }
  }

  private initForm(theme?: Theme): void {
    console.log(theme);
    console.log(this.sessionService.user!.id);
    if( (theme !== undefined) && (theme?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/themes']);
    }
    this.themeForm = this.fb.group({
      tilte: [theme ? theme.title : '', [Validators.required]],
      author: [theme ? theme.author : '', [Validators.required]],
      content: [theme ? theme.content : '', [Validators.required]],
      theme: [theme ? theme.theme : '', [Validators.required]],
    });
    if (!this.onUpdate) {
      this.themeForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private exitPage(themeResponse: ThemeResponse): void {
    //this.matSnackBar.open(themeResponse.messages, "Close", { duration: 3000 });
    this.router.navigate(['themes']);
  }
}