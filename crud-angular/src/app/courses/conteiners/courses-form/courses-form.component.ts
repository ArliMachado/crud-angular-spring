import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AppMaterialModule } from '../../../shared/app-material/app-material.module';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-courses-form',
  standalone: true,
  imports: [AppMaterialModule, ReactiveFormsModule],
  templateUrl: './courses-form.component.html',
  styleUrl: './courses-form.component.scss'
})
export class CoursesFormComponent {
  form = this.formBuilder.group({
    name: ['',],
    category: ['']
  });

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location
  ) {

  }

  onSubmit() {
    this.service.save(this.form.value).subscribe(result => this.onSuccess(),
      error => this.onError());

  }

  onCancel() {
    this.location.back()
  }

  private onSuccess() {
    this.snackBar.open('Curso salvo com sucesso', '', {
      duration: 5000
    });
    this.onCancel();

  }
  private onError() {
    return this.snackBar.open('Erro ao salvar curso', '', {
      duration: 5000
    });
  }
}
