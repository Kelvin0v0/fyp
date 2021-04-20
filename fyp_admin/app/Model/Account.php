<?php

namespace App\Model;


class Account extends Model
{
    //
    protected $table = 'account';
    protected $primaryKey = 'id';
    const CREATED_AT = 'created_at';
    const UPDATED_AT = 'updated_at';

    protected $fillable = [
        'user_id', 'uuid', 'balance','last_login_at'
    ];

}
